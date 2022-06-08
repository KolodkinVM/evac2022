package structure;
import graph.Element;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

 /* Класс, описывающий помещение  (Room, Staircase).      Date of creation (modification)   -  08.06.2022
@author KVM     */

public class Rooms {
    public String sign;				        // Код элемента  Sign =  { Room, Staircase, Outside  } [ Постоянные коды ]
                                            // Код элемента  Sign =  { DangerRoom,   SafetyRoom}   [ Приобретенные коды ]
    public String name_rooms;				// Название элемента в соответствии с планом
    public double numOfPeople;		        // Количество людей (текущее) в элементе, человек
    public String floor;			        // Название уровня в здании
    public double ZLevel;			        // Значение уровня (этажа) помещения, м
    public double sizeZ;			        // Высота элемента (Значимо для Sign =  Staircase ), м
    public double wide_room;		        // Ширина помещения (элемента) (Sign =  Staircase , м
    public double characSize_room;	        // Характерный размер (элемента) (Sign =  Room , м
    public double probability;		        // Вероятность 0-1 прохождения элемента Room, Staircase, DangerRoom, SafetyRoom  [Изменяется в процессе моделирования]
    public double square_room;		        // Значение площади  элемента, м**2	 Определена для Room
    public int direct;				        // Направление движения (определяется при работе программы для Sign =  Staircase )
    public int ntay; 				        // Время обработки Element в единицах tay, мин
    public int numberOutput;		        // Номер выхода DoorWayOut 0, 1, 2...
    public double timeout; 			        // Время достижения зоны безопасности (улицы) через DoorWayOut
    public String note_room;                // Примечание. Дополнительная информация.
    public ArrayList<String> neigh = new ArrayList<String>();	// Идентификаторы дверей (порталов) из помещения



    public Rooms() {    };

    public Rooms (String sign, String name_rooms, double numOfPeople, String floor, double ZLevel, double sizeZ, double wide_room,
                  double characSize_room,  double probability,  double square_room, int direct, int ntay, int numberOutput,double timeout,
                  String note_room ) {
        this.sign = sign;                              // Код элемента  Sign =  { Room, Staircase, Outside, DangerRoom, SafetyRoom} [ Постоянные коды ]
        this.name_rooms = name_rooms;                  // Название элемента в соответствии с планом
        this.numOfPeople = numOfPeople;                // Количество людей (текущее) в элементе, человек
        this.floor = floor;                            // Название уровня в здании
        this.ZLevel = ZLevel;                          // Значение уровня (этажа) помещения, м
        this.sizeZ = sizeZ;                            // Высота элемента (Значимо для Sign =  Staircase ), м
        this.wide_room = wide_room;                    // Ширина помещения (элемента) (Sign =  Staircase , м
        this.characSize_room = characSize_room;        // Характерный размер (элемента) (Sign =  Room , м
        this.probability = probability;                // Вероятность 0-1 прохождения элемента   [Изменяется в процессе моделирования]
        this.square_room = square_room;                // Значение площади  элемента, м**2	 Определена для Room
        this.direct = direct;                           // Направление движения (определяется при работе программы для Sign =  Staircase )
        this.ntay = ntay;                               // Время обработки Element в единицах tay, мин
        this.numberOutput = numberOutput;               // Номер выхода DoorWayOut 0, 1, 2...
        this.timeout = timeout;                         // Время достижения зоны безопасности (улицы) через DoorWayOut
        this.note_room = note_room;                     // Примечание. Дополнительная информация.
    }



// ********* 8/06/2022 Формирование карт room (Room, Staircase, Outside, DangerRoom, SafetyRoom) по зданию
//    roomsMap = structure.Rooms.getRooms(listElement);


public static HashMap<String, Rooms> getRooms(ArrayList<graph.Element> listElement) {
//ArrayList<graph.Element> getlistElement(structure.BuildingPIM building) {
//    public static ArrayList<graph.Element> getlistElement(structure.BuildingPIM building) {
//        ArrayList<Element> listElement = new ArrayList<Element>();        // Список Element (Общий)
    HashMap<String, Rooms> rooms = new HashMap<String, Rooms>();        // Множество Rooms (Общий)
        int kintegral = 0;            // Считаем число элементов в здании
        for (int kk = 0; kk < building.Level.length; kk++) {
        String floorElem = building.Level[kk].NameLevel;
        String ZLevelElem = building.Level[kk].ZLevel;
        for (int kkk = 0; kkk < building.Level[kk].BuildElement.length; kkk++) {
        kintegral++;
        String idElem = building.Level[kk].BuildElement[kkk].Id;
        double dPeopleElem = 0;
        double SizeZElem = building.Level[kk].BuildElement[kkk].SizeZ;
        double WideElem = building.Level[kk].BuildElement[kkk].Wide;
        String NameElem = building.Level[kk].BuildElement[kkk].Name;
        String SignElem = building.Level[kk].BuildElement[kkk].Sign;
        double probabilityElem = 1;
        double SroomElem = building.Level[kk].BuildElement[kkk].Sroom;
        int directElem = 0;
        int ntayElem = 0;
        int numberOutputElem = 0;
        double timeoutElem = 0;
        graph.Element elemx = new graph.Element(idElem,dPeopleElem, floorElem, ZLevelElem, SizeZElem, WideElem, NameElem, SignElem,
        probabilityElem, SroomElem, directElem,0,0,0 );

        for (int k4 = 0; k4 < building.Level[kk].BuildElement[kkk].Output.length; k4++) {
        String NeigtElem = building.Level[kk].BuildElement[kkk].Output[k4];
        elemx.Neigh.add(NeigtElem);
        }
        listElement.add(elemx);
        }
        }
        return listElement;
        }
*/
        }		// end of class

