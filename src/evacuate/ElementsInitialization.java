package evacuate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import structure.BuildingPIM;
/*
import structure.Light;
import structure.LightAdd;
import structure.Room;
import structure.Sensor;
import structure.SensorAdd;
import structure.Transition;
import structure.TransitionAdd;
import structure.Zone;
import structure.ZoneAdd;
import structure.ZoneType; */

    public class ElementsInitialization {

        public ElementsInitialization(BuildingPIM bim) {
            Map<String, ZoneAdd> zonesMap = new HashMap<>();
            Map<String, LightAdd> lightsMap = new HashMap<>();
            Map<String, SensorAdd> sensorsMap = new HashMap<>();

            ZoneAdd za = new ZoneAdd();
            za.id = "-0";
            za.type = ZoneType.FLOOR;
            za.ceilingHeight = 3.0;
            za.note = "Безопасная зона";
            za.setIdInt(0);
            za.setPassability(1.0);
            zonesMap.put("-0", za);

            int idIntZone = 1;
            int idIntLight = 1;
            int idIntSensor = 1;
            for (Room r : bim.rooms) {
                for (Zone z : r.zones) {
                    za = new ZoneAdd();
                    za.id = z.id;
                    za.type = z.type;
                    za.ceilingHeight = z.ceilingHeight;
                    za.fireType = z.fireType;
                    za.numOfPeople = z.numOfPeople;
                    za.note = z.note;
                    za.xyz = z.xyz;
                    za.speakers = z.speakers;
                    za.lights = z.lights;
                    za.sensors = z.sensors;

                    if (z.lights != null) {
                        LightAdd[] las = new LightAdd[z.lights.length];
                        int i = 0;
                        for (Light l : z.lights) {
                            LightAdd la = new LightAdd();
                            la.deviceId = l.deviceId;
                            la.devicePort = l.devicePort;
                            la.id = l.id;
                            la.lat = l.lat;
                            la.lon = l.lon;
                            la.note = l.note;
                            la.type = l.type;
                            la.x = l.x;
                            la.y = l.y;
                            la.z = l.z;
                            la.setReacDevice("passive");
                            la.setIdInt(idIntSensor);
                            la.setParetnIdInt(idIntZone);
                            las[i] = la;
                            i++;
                            idIntSensor++;
                            lightsMap.put(l.id, la);
                        }
                        za.setLightsInt(las);
                    }
                    if (z.sensors != null) {
                        SensorAdd[] sas = new SensorAdd[z.sensors.length];
                        int i = 0;
                        for (Sensor s : z.sensors) {
                            SensorAdd sa = new SensorAdd();
                            sa.deviceId = s.deviceId;
                            sa.id = s.id;
                            sa.note = s.note;
                            sa.type = s.type;
                            sa.x = s.x;
                            sa.y = s.y;
                            sa.z = s.z;
                            sa.setReacDevice("passive");
                            sa.setIdInt(idIntLight);
                            sa.setParetnIdInt(idIntZone);
                            sas[i] = sa;
                            i++;
                            idIntLight++;
                            sensorsMap.put(s.id, sa);
                        }
                        za.setSensorsInt(sas);
                    }

                    za.setIdInt(idIntZone);
                    za.setIdParent(r.id);
                    za.setArea(z.getArea());
                    za.setPassability(1.0);
                    za.setElevation(calculateElevation(za.xyz[0]));
                    za.setzLevel(zMin);
                    za.setDirect(3);
                    idIntZone++;
                    zonesMap.put(z.id, za);
                }
            }

            final double d = Evacuation.NUMBER_OF_PEOPLE / calculateAreaBuilding(zonesMap.values());

            final Iterator<ZoneAdd> iter = zonesMap.values().iterator();
            while (iter.hasNext()) {
                final ZoneAdd z = (ZoneAdd) iter.next();
                z.numOfPeople = d * z.getArea();
            }

            int idIntTransition = 0;
            for (Transition t : bim.transitions) {
                TransitionAdd ta = new TransitionAdd();
                ta.id = t.id;
                ta.doorHeight = t.doorHeight;
                ta.width = t.width;
                ta.zoneAId = t.zoneAId;
                ta.zoneBId = t.zoneBId;
                ta.xyz = t.xyz;
                ta.lightsA = t.lightsA;
                ta.lightsB = t.lightsB;

                ta.setIdInt(idIntTransition);
                ta.setLightsAInt(collectLightsA(lightsMap, t.lightsA));
                ta.setLightsBInt(collectLightsB(lightsMap, t.lightsB));

                for (String lidA : t.lightsA) lightsMap.get(lidA).addTransitions(ta.getIdInt());
                for (String lidB : t.lightsB) lightsMap.get(lidB).addTransitions(ta.getIdInt());

                idIntTransition++;

                ZoneAdd a;
                ZoneAdd b;

                if (ta.zoneAId != null) {
                    a = zonesMap.get(ta.zoneAId);
                    a.addPortal(ta.getIdInt());
                    zonesMap.replace(a.id, a);
                    ta.setZoneAIdInt(a.getIdInt());
                } else {
                    b = zonesMap.get("-0");
                    b.addPortal(ta.getIdInt());
                    zonesMap.replace(b.id, b);
                }

                if (ta.zoneBId != null) {
                    b = zonesMap.get(ta.zoneBId);
                    b.addPortal(ta.getIdInt());
                    zonesMap.replace(b.id, b);
                    ta.setZoneBIdInt(b.getIdInt());
                } else {
                    a = zonesMap.get("-0");
                    a.addPortal(ta.getIdInt());
                    zonesMap.replace(a.id, a);
                }

                transitions.add(ta);
            }

            zones = new ArrayList<ZoneAdd>(zonesMap.values());
            zones.sort(Comparator.comparingInt(ZoneAdd::getIdInt));

            lights = new ArrayList<LightAdd>(lightsMap.values());
            sensors = new ArrayList<>(sensorsMap.values());
        }

        private double zMin;
        private double zMax;

        private double calculateElevation(double[][] xyz) {
            zMin = Double.MAX_VALUE;
            zMax = -Double.MIN_VALUE;
            for (int i = 0; i < xyz.length; i++) {
                double z = xyz[i][2];
                if (z >= zMax) zMax = z;
                if (z <= zMin) zMin = z;
            }
            return zMax - zMin;
        }

        private List<TransitionAdd> transitions = new ArrayList<>(); // Список дверей и проемов в здании
        private List<ZoneAdd> zones; // Список зон в здании
        private List<LightAdd> lights; // Список указателей в здании
        private List<SensorAdd> sensors; // Список сенсоров в здании

        public List<TransitionAdd> getTransition() {
            return transitions;
        }

        public List<ZoneAdd> getZones() {
            return zones;
        }

        public List<LightAdd> getLights() {
            return lights;
        }

        public List<SensorAdd> getSensors() {
            return sensors;
        }

        private double calculateAreaBuilding(Collection<ZoneAdd> za) {
            return za.stream().mapToDouble(ZoneAdd::getArea).sum();
        }

        /**
         * @param lightsMap
         * @param tLightsB
         * @return
         */
        private int[] collectLightsB(Map<String, LightAdd> lightsMap, String[] tLightsB) {
            int[] lightsB = new int[tLightsB.length];
            for (int j = 0; j < lightsB.length; j++) {
                String uuidLightB = tLightsB[j];
                lightsB[j] = lightsMap.get(uuidLightB).getIdInt();
            }
            return lightsB;
        }

        /**
         * @param lightsMap
         * @param tLightsA
         * @return
         */
        private int[] collectLightsA(Map<String, LightAdd> lightsMap, String[] tLightsA) {
            int[] lightsA = new int[tLightsA.length];
            for (int j = 0; j < lightsA.length; j++) {
                String uuidLightA = tLightsA[j];
                lightsA[j] = lightsMap.get(uuidLightA).getIdInt();
            }
            return lightsA;
        }
    }
