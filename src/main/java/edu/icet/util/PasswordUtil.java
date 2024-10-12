package edu.icet.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String encryptPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String encryptedPassword) {
        return BCrypt.checkpw(plainPassword, encryptedPassword);
    }
}
