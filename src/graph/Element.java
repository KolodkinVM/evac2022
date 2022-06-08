package graph;

import java.math.BigDecimal;
import java.util.ArrayList;
import structure.BuildingPIM;
import evacuate.Evacuation;

public class Element {    //		  26.04.2022       07.06.2022
    //Область Outside (Safety zone) формируется на этапе создания помещений. Это зона вне здания, куда эвакуируются люди)
    public String Id;				// Уникальный Идентификатор элемента
    public double dPeople;			// Количество людей (текущее) в элементе, человек
    public String floor;			// Название уровня в здании
    public double ZLevel;			// Значение уровня (этажа) помещения, м
    public double SizeZ;			// Высота элемента (Определено для Sign =  Staircase ), м
    public double Wide;				// Ширина портала (элемента) (Sign =  {  DoorWayOut,  DoorWayInt,  DoorWay}), м
    // DoorWay – Дверной проем
    public String Name;				// Название элемента в соответствии с планом
    public String Sign;				// Код элемента  Sign =  { Room, Staircase,  Outside, Safety zone} [ Постоянные коды ]
    public double probability;		// Вероятность 0-1 прохождения элемента Room, Staircase,  [Изменяется в процессе моделирования]
    public double Sroom;			// Значение площади  элемента, м*м	 Определена для Room

    public int direct;				// Направление движения (определяется при работе программы для Sign =  Staircase )
    public int ntay; 				// Время обработки Element в единицах tay, мин
    public int numberOutput;		      // Номер выхода DoorWayOut 0, 1, 2...
    public double timeout; 			// Время достижения зоны безопасности (улицы) через DoorWayOut

    public ArrayList<String> Neigh = new ArrayList<String>();	// Идентификаторы дверей (порталов) из помещения
    // Идентификаторы помещений, граничащих с дверью

    public Element() {    };
    public Element(String Id, double dPeople,String floor, double ZLevel, double SizeZ, double Wide, String Name, String Sign,
                   double probability,  double Sroom, int direct, int ntay, int numberOutput,double timeout )
    {
        this.Id = Id;
        this.dPeople = dPeople;
        this.floor = floor;
        this.ZLevel = ZLevel;
        this.SizeZ = SizeZ;
        this.Wide = Wide;
        this.Name = Name;
        this.Sign = Sign;
        this.probability = probability;
        this.Sroom = Sroom;
        this.direct = direct;
        this.ntay = ntay;	        this.numberOutput = numberOutput;	        this.timeout = timeout;
     }


    // ********* 24/05/2022 Общий список расчетных элементов (в частном случае помещений+порталов) по зданию
   public static ArrayList<graph.Element> getlistElement(structure.BuildingPIM building) {
       ArrayList<Element> listElement = new ArrayList<Element>();        // Список Element (Общий)
       for (int kk = 0; kk < building.Level.length; kk++) {
           String floorElem = building.Level[kk].NameLevel;
           double ZLevelElem = building.Level[kk].ZLevel;
           for (int kkk = 0; kkk < building.Level[kk].BuildElement.length; kkk++) {
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
               Element elemx = new Element(idElem,dPeopleElem, floorElem, ZLevelElem, SizeZElem, WideElem, NameElem, SignElem,
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



            //    ============			Печать характеристик элемента   28.07.2014        24/05/2022
    public static void ElementPrintFile(graph.Element elemx) {
        Evacuation.Out_result.print(" Element-85= "+ elemx.Name+"   Id= "+elemx.Id+"   Sign= "+ elemx.Sign );
        Evacuation.Out_result.println(" dPeople= "+ elemx.dPeople+"   Sroom= "+elemx.Sroom +"  Channel ="+elemx.numberOutput);
        Evacuation.Out_result.println(" (Elem90) List of Neigh = "  );
        for(int k=0; k< elemx.Neigh.size(); k++) Evacuation.Out_result.print(" ::  "+ elemx.Neigh.get(k));
        Evacuation.Out_result.println();     }


    //    ============     13/05/2014											31/05/2022
    public static void printlistElement(ArrayList<Element> listElement) {
        Evacuation.Out_result.println("   (Element -96) Amount of Elements  " + listElement.size());
        Element xElem = new Element();
        Evacuation.Out_result.print("                Id                                         ");
        Evacuation.Out_result.println("            ZLevel          Sign        SizeZ     Wide            Name	      ");
 //       for (Element xElem : listElement) {
           for (int k = 0; k < listElement.size(); k++ ) { xElem = listElement.get(k);
               Evacuation.Out_result.print(" k= "+k);
 		Evacuation.Out_result.format(" %1$40s     %2$8.1f    %3$15s    %4$8.1f  %5$8.1f        %6$20s     %n",
				xElem.Id, xElem.ZLevel, xElem.Sign, xElem.SizeZ, xElem.Wide, xElem.Name );
              Evacuation.Out_result.println("   List of Neigh " );
            for(int kN=0; kN <xElem.Neigh.size(); kN++) Evacuation.Out_result.print("  "+ xElem.Neigh.get(kN)+"   ");
            Evacuation.Out_result.println();}
    }
}		// end of class



// ********* 12/05/2014 Общий список расчетных элементов (в частном случае помещений+порталов) по зданию
 /*   public ArrayList<graph.Element> getlistElement(VMjson building) {
        int kintegral = 1;			// Считаем число элементов в здании

        for (int kk = 0; kk < building.Level.length; kk++)
            kintegral += building.Level[kk].BuildElement.length;

        // Переход в Id от String к Integer Id = 0, .. Помещения +Id Порталы -Id (Abs(Id) - порядковый номер элемента в
        // массиве
        VMjson.IdBuilding idclass[] = new VMjson.IdBuilding[kintegral];  // Класс с индикаторами
        VMjson tmpVMjson = new VMjson();

        for (int id = 0; id < idclass.length; id++) {
            idclass[id] = tmpVMjson.new IdBuilding();
        }

        int ii = 0;
        int directx = -1000;
        int numberOutputx = 0;

        idclass[0].Idstring = "Outside";
        idclass[0].Idinteger = 0;
        String s2 = "Room";
        String s3 = "Staircase";

        for (int kk = 0; kk < building.Level.length; kk++) {
            for (int kg = 0; kg < building.Level[kk].BuildElement.length; kg++) {
                ii++;
                String s1 = building.Level[kk].BuildElement[kg].Sign;
                if (s1.equals(s2) || s1.equals(s3)) {
                    idclass[ii].Idstring = building.Level[kk].BuildElement[kg].Id;
                    idclass[ii].Idinteger = ii;
                } else {
                    idclass[ii].Idstring = building.Level[kk].BuildElement[kg].Id;
                    idclass[ii].Idinteger = -ii;
                }
                idclass[ii].Sign = s1;
            }
        }

        // Формирование динамического списка элементов - элемент с номером 0
        ArrayList<Element> listElement = new ArrayList<Element>();    	// Список Element (Общий)

        // Создание Room типа Safety zone (вне здания, где люди в безопасности)
        // For Room == Strit -> Id = 0, ZLevel = ZLevel for exit door, Sroom = 1
        double ZLevelout = 0;
        double Sroomout = 1.0;

        Element elemx = new Element(0, 0, ZLevelout, 0.0, 0.0, "Outside", "Outside", 0, Sroomout, directx, 0,
                numberOutputx, 0.0, 0.0, 0, 0);
        String s4 = "DoorWayOut";
        for (int kk = 0; kk < kintegral; kk++) {
            if (idclass[kk].Idinteger < 0) {
                String s1 = idclass[kk].Sign;
                if (s1.equals(s4)) {
                    int numberPortal = idclass[kk].Idinteger;
                    elemx.Neigh.add(numberPortal);
                }
            }
        }

        listElement.add(elemx);

        // Формирование динамического списка элементов - элемент с номером +-1, +-2, +-3 и т.д.
        ii = 0;
        int id = 0;
        for (int kk = 0; kk < building.Level.length; kk++)
            for (int kg = 0; kg < building.Level[kk].BuildElement.length; kg++) {
                ii++;

                int numPeoplex = building.Level[kk].BuildElement[kg].NumPeople;
                //int numPeoplex = getNumberPeople(building.Level[kk].BuildElement[kg].getArea());
                double ZLevelx = building.Level[kk].ZLevel;
                double SizeZx = building.Level[kk].BuildElement[kg].SizeZ;
                String Namex = building.Level[kk].BuildElement[kg].Name;
                int Typex = building.Level[kk].BuildElement[kg].Type;

                double timeblockx = 0;
                double timeoutx = 0.0;
                int scenariox = 0;
                int IDscenariox = 0;

                String s1 = building.Level[kk].BuildElement[kg].Sign;
                String s2 = "Room";
                String s3 = "Staircase";
*/
 /*               if (s1.equals(s2) || s1.equals(s3)) {
                    double Sroomx = building.Level[kk].BuildElement[kg].getArea();
                    elemx = new Element(ii, numPeoplex, ZLevelx, SizeZx, 0.0, Namex, s1, Typex, Sroomx, directx, 0,
                            numberOutputx, timeoutx, timeblockx, scenariox, IDscenariox);
                    elemx.Neigh.clear();

                    for (int out = 0; out < building.Level[kk].BuildElement[kg].Output.length; out++) {
                        String idstr = building.Level[kk].BuildElement[kg].Output[out];
                        for (int k = 0; k < kintegral; k++)
                            if (idstr.equals(idclass[k].Idstring)) elemx.Neigh.add(idclass[k].Idinteger);
                    }
                    id = ii;
                    listElement.add(elemx);
                } else {
                    double Widex = building.Level[kk].BuildElement[kg].getWidth();
                    elemx = new Element(-ii, 0.0, ZLevelx, SizeZx, Widex, Namex, s1, Typex, 0.0, directx, 0,
                            numberOutputx, timeoutx, timeblockx, scenariox, IDscenariox);
                    elemx.Neigh.clear();

                    for (int out = 0; out < building.Level[kk].BuildElement[kg].Output.length; out++) {
                        String idstr = building.Level[kk].BuildElement[kg].Output[out];
                        for (int k = 0; k < kintegral; k++)
                            if (idstr.equals(idclass[k].Idstring)) elemx.Neigh.add(idclass[k].Idinteger);
                    }
                    id = -ii;
                    listElement.add(elemx);
                }

                Identifiers.bindingIdentifiers(building.Level[kk].BuildElement[kg].Id, id);
                // TODO вынести listElement.add(elemx) из условия в конец цикла.
            }

        // Корректировка значений порталов с выходом на улицу (добавление ссылки в портале на улицу)
        elemx = listElement.get(0);
        for (Integer neigh : elemx.Neigh) {
            if (neigh < 0) {
                Element portalStrit = listElement.get(-neigh);
                portalStrit.Neigh.add(0);
            } else {
                Evacuation.Out_result.println(" Mistake Element-166 neigt = " + neigh);
                System.out.println(" Mistake Element-167  neigt = " + neigh);
            }
        }

        elemx.Kontrol(listElement);

        return listElement;
    }

    //============     27.07.2014  Контроль списка элементов	31/07/2014
    public void Kontrol(ArrayList<Element> listElement	) {
        for (Element elemx : listElement)  {	String s1 = elemx.Sign;
            if(elemx.Id < 0 ){
                String s2 = "Room";
                String s3 = "Staircase";
                String s4 = "Outside";
                if ( (s1.equals(s2)) | (s1.equals(s3))  | (s1.equals(s4) ) )
                {Evacuation.Out_result.println(" Mistake Element -178 ");     ElementPrintFile (elemx); };
                int kk = elemx.Neigh.size();
                if ( kk != 2) {Evacuation.Out_result.println(" Mistake Element -180 ");     ElementPrintFile (elemx); }
                for(int k=0; k< kk; k++)
                    if ( elemx.Neigh.get(k) < 0)
                    {Evacuation.Out_result.println(" Mistake Element -183 ");     ElementPrintFile (elemx); };
                if (elemx.Wide <= 0 )
                {Evacuation.Out_result.println(" Mistake Element -185 (Wide<=0) ");     ElementPrintFile (elemx); };
            }
            else {
                String s2 = "DoorWayOut",   s4 = "DoorWay";
                String s3 = "DoorWayInt",   s5 = "Staircase";
                if ( (s1.equals(s2)) | (s1.equals(s3))  | (s1.equals(s4) ) )
                {Evacuation.Out_result.println(" Mistake Element -190 ");     ElementPrintFile (elemx); };
                int kk = elemx.Neigh.size();
                if ( kk < 1) {Evacuation.Out_result.println(" Mistake Element -192 ");     ElementPrintFile (elemx); }
                for(int k=0; k< kk; k++)
                    if ( elemx.Neigh.get(k) >= 0)
                    {Evacuation.Out_result.println(" Mistake Element -195 \n "+elemx.Neigh.get(k));     ElementPrintFile (elemx); };
                if (elemx.Sroom <= 0 )
                {Evacuation.Out_result.println(" Mistake Element -197 (Wide<=0) ");     ElementPrintFile (elemx); };
                if ( (s1.equals(s5)) & (elemx.SizeZ <= 0 ))
                {Evacuation.Out_result.println(" Mistake Element -199 ");     ElementPrintFile (elemx); };
            }	};
    };



    // ********************************** Скорость в проеме, м/мин 31/08/2013 15/05/2014 ******************
    double velem(double l, double dElem) {
        // l - ширина проема, метр
        double v0 = 100; // м/мин
        double d0 = 0.65;
        double a = 0.295;
        double m, v0k;
        if (dElem >= 5) m = 1.25 - 0.05 * dElem;
        else m = 1;
        if (dElem >= 9) v0k = 10 * (2.5 + 3.75 * l) / dElem;
        else {
            if (dElem > d0) v0k = v0 * (1.0 - a * Math.log(dElem / d0)) * m;
            else v0k = v0;
        }
        return v0k;
    }

    //****  Скорость на лестнице, м/мин	// direct = 3 - вверх, = -3 - вниз   //    13.08.02   30/07/2014 *********
    double velemz(int direct, double dElem) {
        double d0 = 0, v0 = 0, a = 0, v0k;
        if (direct == 3) { 	d0 = 0.67;  v0 = 50; a = 0.305; }
        else {
            if (direct == -3) {	d0 = 0.89; 	v0 = 80;  a = 0.4; }
            else  	Evacuation.Out.println(" !!! Mistake Element-226  !!! direct="+direct); } ;
        if (dElem > d0) 	v0k = v0 * (1.0 - a * Math.log(dElem / d0));
        else v0k = v0;
        return v0k;
    }
    //*****	Скорость м/мин  ... - горизонтальный путь в здании     //    13.08.02 		*******
    double velem(double dElem) {
        double v0 = 100; // м/мин
        double d0 = 0.51;
        double a = 0.295;
        double v0k;
        if (dElem > d0) 	v0k = v0 * (1.0 - a * Math.log(dElem / d0));
        else 		v0k = v0;
        return v0k;
    }
    //****	максимально-допустимая плотность, чел/m2 Д.А.Самошин, стр 142.	//		6/08/2013		12/08/2014		*********
    double dmaxxyz(int direct, String Sign) {
 return 5.0;    // Принимаем для упрощения 4.05.2015
    }

    //***     Моделирование Людских потоков по зданию      13/05/2014   изменения 07.08.2014  5.06.2015
//
    public ArrayList<Element>   peopleMoveGraph( ArrayList<Element> listElement,
    double tay, int time, double numPeople )  {


        double Vmax=100.0;  								// метров в минуту
        double dxyz=0.1*tay*Vmax;  							// оценка точности представления координат
        int numExit = listElement.get(0).Neigh.size();		// Number of elements with outputs
        int numRoom = 1;									// Number of Room
        int numDoor = 0;
        for (Element xElem : listElement) {
            if (xElem.Id > 0) numRoom++;
            if (xElem.Id < 0) numDoor++;
        }
//System.out.println(" (Element-265) numRoom = "+numRoom + " numExit = "+  numExit + " numDoor = "+  numDoor);

        ArrayList<ArrayList<Element>> elemOut = new ArrayList<ArrayList<Element>>();  // Списки  на выход  (в количестве numExit);
        for (int ii = 0; ii < numExit; ii++) {	elemOut.add(new ArrayList<Element>()); }
        int k_out[] = new int[numExit]; 			// номер обрабатываемого элемента  списка выхода ii
        int finis_out[] = new int[numExit]; 		// номер последнего элемента списка выхода ii
        int iiWork[] = new int[numExit];     		// номера очередей ii с обрабатываемыми элементами, отсортированными по возрастанию плотности
        double dElem0[] = new double [numExit];		// плотность (раб. массив для сортировки по плотности)

        boolean[] outstep = new boolean[numExit]; 	// признак выхода из цикла do
// int numElemBilTime = 0; 					// Число элементов при расчете (рассчитывается на каждом шаге по времени)

//Формирование и обработка (одновременная) списков к каждому выходу
        for (int kkktay = 1; kkktay <= time; kkktay++) { // Цикл по времени  процесса
// numElemBilTime = 0;
            boolean xyz = false;
            for (int ii = 0; ii < numExit; ii++) outstep[ii] = true;
//											Обработка первого элемента
            Element xElem  = listElement.get(0);
            for (int ii=0; ii < numExit; ii++) {   						//  01  Cycle  of output
                elemOut.get(ii).clear();
                int IdStrPort = xElem.Neigh.get(ii);		 			// Id door for strit (Id=-1)
                double lportal = listElement.get(-IdStrPort).Wide;
                int Idroom1   = listElement.get(- IdStrPort).Neigh.get(0);
                int Idroom2   = listElement.get(- IdStrPort).Neigh.get(1);
                int Idroom= -100000;
                if (Idroom1 == 0 ) Idroom = Idroom2; else Idroom = Idroom1;	// Idroom  -> Id помещения рядом с выходом на улицу
//  System.out.println(" Element-294    IdStrPort= "+IdStrPort + " Idroom = "+  Idroom);
                double dPeopRoom 	= listElement.get(Idroom).dPeople;
                String SignRoom 	= listElement.get(Idroom).Sign;
                String s1 = "Room";
                double SRoom 		= listElement.get(Idroom).Sroom;
                double dElem 		= -10000; // Плотность людей в помещении
                double vElem 		= -10000;
                double vPortal	= -10000;

                if (SignRoom.equals(s1)) {       // Соседнее помещение с улицей "Room";
                    dElem = dPeopRoom / SRoom;
                    vElem = velem(dElem);
                } else {
                    String s2 = "Staircase";
                    if (SignRoom.equals(s2)) {	 	// Соседнее помещение с улицей "Staircase";
                        dElem = dPeopRoom / SRoom;
                        double hElem = listElement.get(Idroom).ZLevel, hElem0 = listElement.get(0).ZLevel;
                        double dh = hElem - hElem0;
                        if (Math.abs(dh) < dxyz) vElem = velem(dElem);
                        else if (hElem > hElem0) {
                            listElement.get(Idroom).direct = -3;
                            vElem = velemz(-3, dElem);
                        } else {
                            listElement.get(Idroom).direct = 3;
                            vElem = velemz(3, dElem);
                        }
                    } else {
                        Evacuation.Out_result.println(" Mistake Element -317 ");
                        ElementPrintFile(xElem);
                    }
                }
                //	System.out.println( "Element #0  Id = "+Idroom + "  SignRoom = "  + SignRoom + "    dPeopRoom = "+dPeopRoom + " SRoom = "+  SRoom  );
                vPortal = velem(lportal, dElem);
                double v 	= Math.min(vElem, vPortal);      // Скорость на выходе из здания
                double d1	= lportal*v*tay/SRoom;
                double d2	= -10000;
                double ddPeople = -10000;
                if (d1 >= 1) d2 = 1; else d2 = d1;
                double dPeopl = d2*dPeopRoom;  									//  Изменение численности людей в помещении рядом с выходом
                double delta = listElement.get(Idroom).dPeople - dPeopl;			//
                if (delta > 0) ddPeople = dPeopl; else ddPeople = dPeopRoom;
                listElement.get(0).dPeople += ddPeople;							//  Увеличение людей вне здания
                listElement.get(Idroom).dPeople -= ddPeople;						//  Уменьшение людей в элементе здания
                if (listElement.get(Idroom).dPeople < 0) listElement.get(Idroom).dPeople = 0;
                listElement.get(- IdStrPort).dPeople += ddPeople;					//  Увеличение людей через дверь ii
                listElement.get(- IdStrPort).ntay++;								//  Признак обработки двери
                listElement.get(- IdStrPort).numberOutput = ii;					//  Выход через дверь ii на улицу
                int indp  = listElement.get(- IdStrPort).ntay;
//System.out.println( "Element-320 Elem##0  Id = "+Idroom + "  dPeopl = "  + dPeopl + "    indp = "+indp+
//  "   listElement.get(- IdStrPort).ntay  "+listElement.get(- IdStrPort).ntay+"  #Output="+listElement.get(- IdStrPort).numberOutput);
                listElement.get(Idroom).ntay = indp;								//  Признак обработки элементa здания
                listElement.get(Idroom).numberOutput = ii;						//  Помещение освобождается через выход ii
                if (v > 0) listElement.get(Idroom).timeout = Math.sqrt(SRoom)/v;
                else listElement.get(Idroom).timeout = 0;						//  Потенциал времени в первом помещении
                elemOut.get(ii).add(listElement.get(Idroom));  //numElemBilTime ++;
                k_out[ii] = 0;
                finis_out[ii] = 0;
                // System.out.println( "Elem-345 #0 Обр-ка Id = "+Idroom + "  SignRoom = "  + SignRoom + "    dPeopl = "+dPeopl // + " SRoom = "+  SRoom
                //		  +"  ntay = "+ indp +"  #Output="+listElement.get(- IdStrPort).numberOutput+ "  numElemBilTime="+numElemBilTime);
            }     //  01  Cycle  of output			// Finis of the cycle  of output


//Формирование последующих элементов в каждом списке к выходу
            do { 	//  1  Проход по ячейкам и всасывание людей из ячеек, соседних с наполняемой ячейкой   elem0	цикл по ячейкам на шаге tay

                for (int ii = 0; ii < numExit; ii++) {
                    dElem0[ii] = 1.0E+10;
                    iiWork[ii] = numExit - 1;
                    if (outstep[ii] == true) {
                        Element elem0 = elemOut.get(ii).get(k_out[ii]);
//                        dElem0[ii] = elem0.dPeople / elem0.Sroom;
                        dElem0[ii] = elem0.timeout;
                        iiWork[ii] = ii;
                    }
                }
                // Сортировка элементов firstElem по времени достижения зоны безопасности    4.06.2015 //плотности
                for (int i = numExit - 1; i >= 0; i--) {  	// Сортировка
                    for (int j = 0; j < i; j++) {
                        if (dElem0[j] > dElem0[j + 1]) {	 	// Сортировка ->  Увеличение времени по массиву  элементов, в которые засасываются люди
//		if (dElem0[j] < dElem0[j + 1]) { 		// Сортировка ->  Уменьшение плотности по элементам источников.
                            double dd = dElem0[j];    dElem0[j] = dElem0[j + 1];	dElem0[j + 1] = dd;
                            int nn = iiWork[j];       iiWork[j] = iiWork[j + 1];	iiWork[j + 1] = nn;
                        }	}	} 								// Завершение Сортировки

                for (int iiTurn = 0; iiTurn < numExit; iiTurn++) { 	// Cycle  of output
                    int ii = iiWork[iiTurn];
                    if (ii < 0) {  System.out.println( ); System.out.println( " !!!   Mistake Elem-365 ii="+ii+ "  outstep[ii]= " +outstep[ii] +
                            "  iiTurn ="+iiTurn +"  numExit = "+ numExit);
                        for (int ii2 = 0; ii2 < numExit; ii2++)     System.out.println( " iiWork["+ii2+"]="+iiWork[ii2]+"  dElem0[ii]="+dElem0[ii2]);
                        System.out.println( );}

                    boolean constDirec = false;
                    do {													// 02 - boolean constDirec = true;
                        boolean noEmpty = false;
                        if (outstep[ii] == true) {                              // 2 outstep[ii] == true
                            Element elem0 = elemOut.get(ii).get(k_out[ii]);		// Выбираем элемент списка, в который будем "засасывать" людей
                            int nii = elem0.Neigh.size();							// Количество дверей элемента
                            //    System.out.println( );
//     System.out.println( " Element-370 ii = "+ii+"  k_out[ii]="+ k_out[ii]+"  elem0.Id = "+ elem0.Id+
//   			 "  elem0.Neigh.size() = "+nii +"    finis_out[ii]="+  finis_out[ii]+ "  numElemBilTime="+numElemBilTime);
                            for (int iip=0; iip < nii; iip++) {   				// Cycle  on portal
                                int ip = - elem0.Neigh.get(iip);					// Id portal
	    Out  ( " Elem-374 ii="+ii+"  iip="+iip+"  elem0.Id = "+ elem0.Id+"  elem0.Neigh.size() = "+nii+  "  Idportal = "+ip+
			 " elem0.ntay " + elem0.ntay+"  elem0.ii "+elem0.numberOutput+"  ntay portal "+listElement.get(ip).ntay+
			 "  ii portal "+listElement.get(ip).numberOutput);// + " numElemBilTime="+numElemBilTime);
                                if ((elem0.ntay > listElement.get(ip).ntay ) )   {				// Портал подлежит обработке
                                    double lportal = listElement.get(ip).Wide;						// Ширина портала
                                    int Idroom1 = 	listElement.get(ip).Neigh.get(0);		   		//Ссылка от портала на помещение 0
                                    int Idroom2 = 	listElement.get(ip).Neigh.get(1);
                                   	//Ссылка от портала на помещение 1
 //                                   int Idroom  = 	-10000;
   //                                 int IdroomOld = 	elem0.Id;
     //                               if (Idroom1 == IdroomOld ) Idroom = Idroom2; else Idroom = Idroom1;	// Idroom - сcылка на соседа  Погасили обратный ход
                                    //    System.out.println(" Element-348   Id portal= "+ip + "  Ширина портала "+lportal+ "  Ссылка от портала = "+  Idroom);
                                    double dPeopRoom 	= listElement.get(Idroom).dPeople;          // Количество людей в соседней ячейке
                                    double SRoom 		= listElement.get(Idroom).Sroom;  			// Площадь соседней ячейки
                                    String SignRoom 	= listElement.get(Idroom).Sign;
                                    double dElem 		= -10000;
                                    double vElem 		= -10000;
                                    double vPortal		= -10000;
                                    int direct			= -10000;
                                    String s1 = "Room";
                                    String s2 = "Staircase";
                                    if   (SignRoom.equals(s1)) {      								// Соседнее помещение Room ;
                                        dElem = dPeopRoom/SRoom; 		 vElem = velem(dElem);	 direct = 0;}
                                    else {
                                        if   (SignRoom.equals(s2)) {	 		 // Соседнее помещение  "Staircase";
                                            dElem = dPeopRoom/SRoom;
                                            double hElem = listElement.get(Idroom).ZLevel, hElem0 = elem0.ZLevel;
                                            double dh = hElem - hElem0;
                                            if (Math.abs(dh)< dxyz) { direct = 0; vElem = velem(dElem); } else
                                            if (hElem > hElem0)	{ direct = -3; vElem = velemz(direct, dElem);} else {direct = 3; vElem = velemz(direct, dElem); } }
                                        else {Evacuation.Out_result.println(" Mistake Element -405 ");     ElementPrintFile (xElem); } 	}
                                    //	System.out.println( "Element #0  Id = "+Idroom + "  SignRoom = "  + SignRoom + "    dPeopRoom = "+dPeopRoom + " SRoom = "+  SRoom  );
                                    vPortal = velem(lportal, dElem);
                                    double v 	= Math.min(vElem, vPortal);      // Скорость на выходе  из зоны (помещения)
                                    double d1	= lportal*v*tay/SRoom;
                                    double d2	= -10000;
                                    double ddPeople = -10000;
                                    if (d1 >= 1) d2 = 1; else d2 = d1;
                                    double dPeopl = d2*dPeopRoom;										//  Изменение численности людей в помещении, откуда высасываются люди
                                    double delta = dPeopRoom - dPeopl;			 						//	dPeopRoom - кол. людей в ячейке, откуда засасываем
                                    if (delta > 0) ddPeople = dPeopl; else ddPeople = dPeopRoom;		//  ddPeople - кол. людей, которые могут быть высосаны
                                    if   ( (elem0.Sign).equals(s2) && (direct <0 ) ) direct = 0;
                                    double dmaxElem0 = elem0.dmaxxyz(direct, elem0.Sign);  	// максимально-допустимая плотность  в помещении, куда засасываются люди
                                    if (dmaxElem0 < 0 ){Evacuation.Out_result.println(" Mistake Element -417  dmaxElem0= "+ dmaxElem0);
                                        ElementPrintFile (elem0); }
                                    double maxPeople = dmaxElem0*elem0.Sroom; 				    // max nubber people in room  elem0 (куда засасываем)
//		   double numPeople = listElement.get(IdroomOld).dPeople;
                                    double numP = maxPeople - elem0.dPeople;						// Вместимость элемента elem0 на данный момент времени
                                    double changePeople = -10000;
                                    if (numP > ddPeople) changePeople = ddPeople; else changePeople = numP;
                                    listElement.get(IdroomOld).dPeople += changePeople;			//  Увеличение людей в elem0
                                    listElement.get(Idroom).dPeople -= changePeople;				//  Уменьшение людей в элементе здания
                                    if (listElement.get(Idroom).dPeople < 0) listElement.get(Idroom).dPeople = 0;
                                    listElement.get(ip).dPeople += changePeople;					//  Увеличение людей через дверь ii
                                    listElement.get(ip).ntay++;									//  Признак обработки двери
                                    listElement.get(ip).numberOutput = ii;
                                    int indp = listElement.get(ip).ntay;
                                    //System.out.println( "Element-430 Elem##0  Id = "+Idroom + "  dPeopl = "  + dPeopl + "    indp = "+indp+
                                    //  "   listElement.get(- IdStrPort).ntay  "+listElement.get(- IdStrPort).ntay+"  #Output="+listElement.get(- IdStrPort).numberOutput);
                                    listElement.get(Idroom).ntay = indp;							//  Признак обработки элементa здания
                                    listElement.get(Idroom).numberOutput = ii;						//  Помещение освобождается через выход ii
                                    if (v > 0) listElement.get(Idroom).timeout = elem0.timeout+ Math.sqrt(SRoom)/v;
                                    else listElement.get(Idroom).timeout =elem0.timeout;
                                    elemOut.get(ii).add(listElement.get(Idroom));  //  numElemBilTime ++;
                                    finis_out[ii] ++ ;
//         System.out.println( "Element-435 ii="+ii+"  iip="+iip+" Обработка Id = "+Idroom + "  SignRoom = "  + SignRoom + "    changePeople = "+changePeople +
                                    //			" SRoom = "+  SRoom +"  ntay = "+ indp+ "  numElemBilTime="+numElemBilTime );
                                    //   System.out.print(" Elem-391 numElemBilTime = "+numElemBilTime);
                                    //   System.out.println("  elem0.Id = "+elem0.Id + " Обраб.Id = "+  Idroom //+ " (Idroom).ntay= "  + indp
                                    //		   +" (Idroom).dPeople = "+ changePeople);
                                }
                            }     // Обработка списка порталов
                            if ( finis_out[ii] >	 k_out[ii] ) {k_out[ii]++ ; constDirec = true;} else {  outstep[ii] = false;  constDirec = false;
                                //  System.out.println( "Element-404 ii="+ii+"  finis_out[ii] = "+ finis_out[ii] + "  k_out[ii] = "  + k_out[ii] );
                            }

                        }          //  2  outstep[ii] == true
                        if (constDirec == true) {								//  5/06/2015
                            int iikd = iiTurn + 1;
                            if (iikd >= numExit ) constDirec = false;
                            else { if (elemOut.get(ii).get(k_out[ii]).timeout > dElem0[iikd] ) { constDirec = false;  }else {
                                // System.out.println( "Element-533   " +  elemOut.get(ii).get(k_out[ii]).timeout +"   "+ dElem0[iikd]);
                                constDirec = true;} };
                        }
                    }  while (constDirec) ;   // do {   // 02 - finis

                } // Обработка очереди ii
                xyz = false;
                for (int ii1=0; ii1 < numExit; ii1++)   if (outstep[ii1] == true) xyz = true;
            }     while (xyz); 		// do  1

            double numP = 0;
            for (Element elemx : listElement)
                if (elemx.Id > 0) {
                    if (elemx.dPeople > 0.5)
                        numP += elemx.dPeople;
                }
            if (numP < numPeople) {
                listElement.get(0).numberOutput = -kkktay;
                return (listElement);
            } // Выход из цикла моделирования (В здании нет людей).

        };      // Цикл по времени  процесса

        return ( listElement);

    }

    /**
     * Метод задания количества людей в помещении в зависимости от плотности. Создан для тестирования. Возникнет
     * погрешность при округлении произведения до целых.
     *
     * @param squareElement - площадь элемента (помещения)
     * @return Количество человек
     */
    /*
    private int getNumberPeople(double squareElement) {
        final float DENSYTI = 0.2f;

        return new BigDecimal("" + (DENSYTI * squareElement)).intValue();
    }*/



