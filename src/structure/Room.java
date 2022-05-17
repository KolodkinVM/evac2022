package structure;

import java.util.ArrayList;

/** Класс, описывающий помещение.           Date of creation (modification)   -  17/05/2022
 * @author KVM     */

public class Room {

    public String name_room;				// Название элемента в соответствии с планом
    public String id_room;				    // Уникальный Идентификатор элемента
    public double numOfPeople;		        // Количество людей (текущее) в элементе, человек
    public String floor;			        // Название уровня в здании
    public double ZLevel;			        // Значение уровня (этажа) помещения, мм
    public double sizeZ;			        // Высота элемента (Значимо для Sign =  Staircase ), мм
    public double wide_room;		        // Ширина помещения (элемента) (Sign =  Staircase , мм
    public String sign;				        // Код элемента  Sign =  { Room, Staircase,  Outside, Safety zone} [ Постоянные коды ]
    public double probability;		        // Вероятность 0-1 прохождения элемента Room, Staircase,  [Изменяется в процессе моделирования]
    public double square_room;		        // Значение площади  элемента, м**2	 Определена для Room
    public int direct;				        // Направление движения (определяется при работе программы для Sign =  Staircase )
    public int ntay; 				        // Время обработки Element в единицах tay, мин
    public int numberOutput;		        // Номер выхода DoorWayOut 0, 1, 2...
    public double timeout; 			        // Время достижения зоны безопасности (улицы) через DoorWayOut
    public String note_room;                     // Примечание. Дополнительная информация.
    public ArrayList<String> neigh = new ArrayList<String>();	// Идентификаторы дверей (порталов) из помещения

}