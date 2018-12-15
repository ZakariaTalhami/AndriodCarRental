package com.rental.shaltal.carrental.helpers;

import android.util.Log;

import com.rental.shaltal.carrental.constants.GenderEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rental.shaltal.carrental.constants.AppConstants.*;

public class RegisterValidator {

    private static final String TAG = "RegisterValidator";

    public static boolean validateName(String text){
        boolean valid = false;
        try{
            if (text != null){
                valid = Pattern.matches(VAL_REG_NAME , text);
            }
        }
        catch (Exception e){
            Log.e(TAG, "validateName: Failed to validate the Name "+text+"\n", e );
        }
        return valid;
    }

    public static boolean validateEmail(String text){
        boolean valid = false;
        try{
            if (text != null){
                valid = Pattern.matches(VAL_REG_EMAIL , text);
            }
        }
        catch (Exception e){
            Log.e(TAG, "validateName: Failed to validate the Email "+text+"\n", e );
        }
        return valid;
    }

    public static boolean validateGender(String text){
        boolean valid = false;
        try{

            for (GenderEnum c : GenderEnum.values()) {
                if (c.name().equalsIgnoreCase(text)) {
                    return true;
                }
            }
        }
        catch(Exception e){
            Log.e(TAG, "validateGender: Failed to validate Gender "+text+"\n",e );
        }
        return valid;
    }

    public static boolean validatePassword(String text){
        boolean valid = false;
        try{
            if (text != null){
                valid = Pattern.matches(VAL_REG_PASS , text);
            }
        }
        catch (Exception e){
            Log.e(TAG, "validateName: Failed to validate the Password "+text+"\n", e );
        }
        return valid;
    }

    public static boolean validatePhone(String text, String Country){
        boolean valid = false;
        try{
            if (text != null){
                valid = Pattern.matches(VAL_REG_PHONE , text);
            }
        }
        catch (Exception e){
            Log.e(TAG, "validateName: Failed to validate the PHone "+text+"\n", e );
        }
        return valid;
    }
}
