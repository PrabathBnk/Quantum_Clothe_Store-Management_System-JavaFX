package edu.icet.util;

public class ValidationUtil {

    public static boolean isValidEmail(String email) {
        return email.matches("\\w+[\\w.]+@\\w+[.]\\w+");
    }

    public static boolean isValidContactNumber(String contact) {
        return (contact.startsWith("0") || contact.startsWith("+")) && contact.matches("[+0]\\d{9,}");
    }
}
