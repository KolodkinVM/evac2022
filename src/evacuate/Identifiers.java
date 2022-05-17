package evacuate;

import java.util.HashMap;

/*       25/04/2022

 */
public class Identifiers {
    private static HashMap<String, Integer> ids = new HashMap<>();

    /**
     * Метод получения массива пар UUID=ID
     *
     * @return HashMap (String, Integer)
     */
    public static HashMap<String, Integer> getIdentifiers() {
        return Identifiers.ids;
    }

    /**
     * Метод установки пары UUID=ID
     *
     * @param uuid - идентификатор полученный из Json
     * @param id - идентификатор созданный ВМ из UUID
     */
    public static void bindingIdentifiers(String uuid, int id) {
        Identifiers.ids.put(uuid, id);
    }
}
