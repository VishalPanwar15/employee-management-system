package com.employee.utilityclass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityMethods {

    public static boolean isValidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);

        if(email==null){
            return false;
        }
        else{
            return pattern.matcher(email).matches();
        }
    }

    public static boolean isValidPassword(String password){
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        if(password == null){
            return false;
        }
        else {
            return pattern.matcher(password).matches();
        }
    }
}
