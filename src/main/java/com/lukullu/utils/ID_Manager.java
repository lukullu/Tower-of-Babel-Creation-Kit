package com.lukullu.utils;

public class ID_Manager {

    private static ID_Manager single_instance = null;

    public static synchronized ID_Manager getInstance()
    {
        if (single_instance == null)
            single_instance = new ID_Manager();

        return single_instance;
    }

    private int iterator = 0;

    public int generateNewID()
    {
        try {
            if (Integer.MAX_VALUE - 1 == iterator) {
                throw new Exception("RanOutOfIDsException: You actually have to deal with this Problem now you lazy ass.");
            }
            return iterator++;
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return -1;
    }

}
