package structure;

import java.text.DecimalFormat;
import java.util.Arrays;
import evacuate.Evacuation;

/**    Редакция   29.04.2022 - 11.05.2022     ====
 * Класс используется для десериализации стандарта BECS.json
 * @author mag          Kolodkin      */
public class BuildingPIM {
    public String nameBuilding;         // Общее название здания
    public String program_name;         // Программа создания файла Json
    public String version_program;      // Версия (дата последней модификации программы)

    public int numberOfRoom;
    public int numberOfDoor;
    public int numberofDoorWayInt;
    public int numberOfDoorWayOut;
    public int numberOfStaircase;
    public String date_creation_Json;           // Дата создания файла Json
    public Address_Building address_building;     //  Адресные данные
    public Level_Building[] Level;               //  Класс уровней здания

    // --------------------------------------------------

    //       * Внутренний класс для указания адреса - Address.
    public class Address_Building {
        public String city;             //     Город.
        public String streetAddress;    //  Улица, дом.
        public String addInfo;          //  Допол. инфо
    }

    //       * Внутренний класс для указания уровней здания - Level_Bilding.
    public class Level_Building {
        public String NameLevel;                // Имя уровня\
        public double ZLevel;                   //Положение уровня по высоте, м
        public Element_Json[] BuildElement;

        //       * Внутренний класс для указания элемента Json - Element_Json.
        public class Element_Json {
            public String Name;                  // Имя элемента
            public String Id;                    // Id элемента
            public String Sign;                  // Код элемента
            public double  SizeZ;                // Размер по высоте элемента, м
            public double   Wide;                 // Размер по ширине элемента, м
            public double   Sroom;                // Площадь, м*м
            public String[] Output;             // Список указателей на элементы
        }
    }


 public static void printJson(BuildingPIM build) {
     System.out.println("  (BuildingPIM -53)   	nameBuilding  " + build.nameBuilding+"     program_name  " + build.program_name);
     System.out.println("  (BuildingPIM -54)   	numberOfRoom  " + build.numberOfRoom+"     numberOfDoor  " + build.numberOfDoor);
     System.out.println("  (BuildingPIM -55)   	numberofDoorWayInt  " + build.numberofDoorWayInt+
             "     numberOfDoorWayOut  " + build.numberOfDoorWayOut+"     numberOfStaircase  " + build.numberOfStaircase);
      for (int kk = 0; kk < build.Level.length; kk++) {
         System.out.println(" Level[kk].NameLevel    " + build.Level[kk].NameLevel);
         System.out.println(" Level[kk].ZLevel    " + build.Level[kk].ZLevel + " m");
         for (int kkk = 0; kkk < build.Level[kk].BuildElement.length; kkk++) {
             System.out.println(" build.Level[kk].BuildElement[kkk].Name     " + build.Level[kk].BuildElement[kkk].Name);
             System.out.println(" build.Level[kk].BuildElement[kkk].Id    " + build.Level[kk].BuildElement[kkk].Id);
             for (int k4 = 0; k4 < build.Level[kk].BuildElement[kkk].Output.length; k4++)
                 System.out.println("  k4 = , Id =  " + k4 + " ,   "+build.Level[kk].BuildElement[kkk].Output[k4]);
         }
     }
 }

    public void printJsonFile(double tay, double hxy) {
        DecimalFormat f1 = new DecimalFormat("#.000");
        for (String s : Arrays.asList(" BuildingPIM-62 Идентификатор здания		 		" + nameBuilding,
                "  Программа создания  "   +  program_name,  "  Версия программы "   +  version_program,
                "  Дата создания программы "   +  date_creation_Json,
                "  Шаг моделирования - tay,  мин = 	" + f1.format(tay), "  Шаг по пространству - hxy, м = 	" + f1.format(hxy))) {
            Evacuation.out_support.println(s);
        }
    }
}

     /*
       for (int kk = 0; kk < rooms.length; kk++) {
          Evacuation.out_support.println(" rooms[" + kk + "].name    " + rooms[kk].name + "   " + rooms[kk].id);

            for (int k1 = 0; k1 < rooms[kk].zones.length; k1++)
                Evacuation.out_support.println(
                        " zones[" + k1 + "].zoneType    " + rooms[kk].zones[k1].type + "   " + rooms[kk].zones[k1].id);
        }
        for (int kk = 0; kk < transitions.length; kk++) {
            Evacuation.out_support.println(" transitions[" + kk + "].id    " + transitions[kk].id + " AId   "
                    + transitions[kk].zoneAId + " BId  " + transitions[kk].zoneBId);

        }

      ==========   4/05/2022    ======
    public class Address_Bilding;
}
   public String date_creation_Json;        // Дата создания файла Json
    public Address address;            //  Адресные данные
    public Room[] rooms;               // Массив помещений в здании
    public Transition[] transitions;   //** Массив переходов/дверей/проемов/лестниц(сборок) в здании

public String nameBuilding;                 // Общее название здания
    public String program_name;                 // Программа создания файла Json
    public String version_program;              // Версия (дата последней модификации программы)

*/