package evacuate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Stream;

import structure.BuildingPIM;
import graph.Element;
/*import structure.LightAdd;
import structure.TransitionAdd;
import structure.ZoneAdd;*/


public class Evacuation {         // 28.04.2014  13.05.2022
    // Здание представляется двудольным графом.  Одна совокупность вершин графа -
    // области перемещений людей (помещение, часть помещения, лестница и т.д), вторая совокупность -
    // портал (дверь, проем ...)

    // Коэфф., определяющий макс. плотность в ячейке (площадь гориз. проекции человека, м2/чел)
    public static final double FP = 0.113;
    public final static double NUMBER_OF_PEOPLE = 200; 	// Количество людей в здании
    public static final double CrPassability = 0.1;		// Критическая проходимость зоны

    public static PrintStream out_support; 				// Файл промежуточных результатов
    public static PrintStream out_results; 				// Файл итоговых результатов
    public static PrintStream out_total; 				// Файл TOTAL итоговых результатов

    private static BuildingPIM building;

    public static void main(String[] args) {
        final String outputLocation = "C:/Users/KolodkinVM/Documents/A3-Work2022/"; // Директория локализации файлов с результатами
        final String jsonLocation = "source/vmtest1.json"; // Json - файл со зданием тестовое здание
        final LoadJson loadingJson = new LoadJson(jsonLocation); // Десериализуем файл json
        building = loadingJson.getBuilding();
/*        System.out.println( building.nameBuilding);
        System.out.println( " Evacuation - 40  " + building.program_name);
        System.out.println( building.version_program);  */
  //      System.out.println("   Evacuation -42 ");
        BuildingPIM.printJson(building);
     //   final ElementsInitialization initElementsLists = new ElementsInitialization(building); // Собираем списки элементов
//        final StatusBarData barData = new StatusBarData();
//        System.out.println("   Evacuation -46 ");
        // Подготовка файла для печати результатов (вспомогательная информация)
        System.out.println("   Evacuation -48 "+ outputLocation);
        outputSettings(outputLocation, building);
        System.out.println("   Evacuation -49 ");
        // --------------------------------------------------------//

        final double hxy = 0.5;		// характерный размер области, м
        final double ktay = 0.5; 	// коэффициент (<1) уменьшения шага по времени для устойчивости расчетов
        final double vmax = 100; 	// максимальная скорость эвакуации, м/мин
        final double tay = (hxy / vmax) * ktay; 	// мин - Шаг моделирования 100 - максимальная скорость м/мин
        System.out.println("   Evacuation -55 ");
        building.printJson(building);
            System.out.println("   Evacuation -60 ");
        building.printJsonFile(tay,hxy); 			// Печать параметров здания в файл
            System.out.println("   Evacuation -62 ");
/*
        List<ZoneAdd> zonesList = initElementsLists.getZones();		// Список зон в здании
        List<TransitionAdd> transitionsList = initElementsLists.getTransition();	// Список дверей в здании
        List<LightAdd> lightsList = initElementsLists.getLights(); 	// Список указателей в здании

        Evacuation.out_results.println("\t" + "List of Zones ");
        Evacuation.out_results.println("\t" + "id_Zone       id_Room   zoneType      Peop    s_Zone");
        zonesList.stream().forEach(e -> Evacuation.out_results.println(e));

        Evacuation.out_results.println();
        Evacuation.out_results.println("\t" + "List of Transition");
        Evacuation.out_results.println("\t" + "  id      width   zoneAId    zoneBId");
        transitionsList.stream().forEach(e -> Evacuation.out_results.println(e)); // Печать списка дверей в здании

        Evacuation.out_results.println();
        Evacuation.out_results.println("\t" + "List of Light");
        Evacuation.out_results.println("\t" + "  id      isActive    type       transitions");
        lightsList.stream().forEach(e -> Evacuation.out_results.println(e)); // Печать списка дверей в здании
*/
        double timeModel = 0; 	// Моделируемый момент времени
        final int iter = 50; 	// Цикл моделирования
        final int time = 50; 	// Интервал моделирования в единицах tay ,мин

//		UI.controllers = new Controllers(building, zonesList);
//        Thread uiThread =  new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Application.launch(new UI().getClass(), args);
//            }
//        });
//        uiThread.start();

        // Два элемента массива зарезервированы под первую и последнюю строку
 /*       String[] lineReport = new String[zonesList.size()+2];
        generateReport(zonesList, lineReport, timeModel,  " id ", "total");
        zonesList.stream().forEach(e->{
            if(e.getIdInt()==12 || e.getIdInt() == 17) e.setPassability(0);
        });
        DecimalFormat f1 = new DecimalFormat("#.000");
        Traffic traffic = new Traffic();
*/
        System.out.println("  Tay "+tay);

  /*      for (int kiter = 0; kiter < iter; kiter++) {
//		    playControl();
            zonesList = traffic.foot_traffic(zonesList, transitionsList, lightsList, tay, time);
            if (zonesList.get(0).numberOutput  < 0) {
                timeModel += -tay * zonesList.get(0).numberOutput;
                Evacuation.out_results.println();
                Evacuation.out_results.println("    TimeFinis = " + f1.format(timeModel));
                System.out.println("TimeFinis = " + f1.format(timeModel));
                // Печать распределения людей по помещениям
                generateReport(zonesList, lineReport, timeModel, "", "");
                Stream.of(lineReport).forEach(System.out::println);
                break;
            } else timeModel += tay * time;
            generateReport(zonesList, lineReport, timeModel, "", "");
            Stream.of(lineReport).forEach(System.out::println);
        }
        */
    }



    /*
     * @param zonesList
     * @param lineReport
     * @param timeModel
     * @param barData
     * @return
     */
 /*
    private static String[] generateReport(List<ZoneAdd> zonesList, String[] lineReport, double timeModel,
                                           String prefixHeader, String prefixFoother) {
        final int lastLine = lineReport.length-1;
        if (lineReport[0] == null) for (int i = 0; i < lineReport.length; i++) lineReport[i] = "";
        lineReport[0] += prefixHeader + "\t" + "t=" + timeModel; // шапка
        double numberTotalPeople = 0.0;
        for (int i = 0; i < zonesList.size(); i++) {
            ZoneAdd e = zonesList.get(i);
            String body = null;
            if (!prefixFoother.equals("")) body = " " + e.idInt + "\t" + e.numOfPeople + " "; // тело
            else body = "\t" + e.numOfPeople + " ";
            lineReport[i + 1] += body;
            numberTotalPeople += e.numOfPeople;
        }
        lineReport[lastLine] += prefixFoother + "\t" + numberTotalPeople + " "; // сводка

        return lineReport;
    }
*/
    /*
     * @param nameLocation
     * @param timecor
     * @param nameBuilding
     */

    private static void outputSettings(final String nameLocation, final BuildingPIM building) {
        final long timestap = System.currentTimeMillis();
        final String nameBuilding = building.nameBuilding;
        final String nameFile_sup = nameLocation + nameBuilding + "-" + timestap + "(support).txt".replace("\n", "-");
        try {
            out_support = new PrintStream(new FileOutputStream(nameFile_sup, true), true);
        } catch (FileNotFoundException e) {
            try {
                out_support = new PrintStream(new FileOutputStream(nameFile_sup), true);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
                return;
            }
        }

        // Подготовка файла для печати результатов (результаты)
        final String nameFile_res = nameLocation + nameBuilding + "-" + timestap + "(result).txt".replace("\n", "-");
        try {
            out_results = new PrintStream(new FileOutputStream(nameFile_res, true), true);
        } catch (FileNotFoundException e) {
            try {
                out_results = new PrintStream(new FileOutputStream(nameFile_res), true);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
                return;
            }
        }

        // Подготовка файла для печати TOTAL результатов
        final String nameFile_total = nameLocation + nameBuilding + "-" + timestap + "(total).txt".replace("\n", "-");
        try {
            out_total = new PrintStream(new FileOutputStream(nameFile_total, true), true);
        } catch (FileNotFoundException e) {
            try {
                out_total = new PrintStream(new FileOutputStream(nameFile_total), true);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
                return;
            }
        }
    }

}

