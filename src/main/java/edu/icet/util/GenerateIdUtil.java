package edu.icet.util;

public class GenerateIdUtil {

    public static String generateId(String initialIdentifyText, String lastId){
        return initialIdentifyText + String.format("%03d", (Integer.parseInt(lastId.substring(3)) + 1));
    }
}
