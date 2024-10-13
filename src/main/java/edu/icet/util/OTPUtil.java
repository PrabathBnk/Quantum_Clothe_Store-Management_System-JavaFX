package edu.icet.util;

import java.time.LocalTime;
import java.util.Random;

public class OTPUtil {

    private static String otp;
    private static LocalTime expiryTime;

    public static String generateOTP(){
        Random random = new Random();
        otp = "";
        for (int i = 0; i < 6; i++) otp += random.nextInt(10);

        //Set OTP expiry time
        expiryTime = LocalTime.now().plusMinutes(5);

        return otp;
    }

    public static boolean isOTPExpired(){
        return LocalTime.now().isAfter(expiryTime);
    }

    public static boolean checkOTP(String userOtp){
        return otp.equals(userOtp);
    }
}
