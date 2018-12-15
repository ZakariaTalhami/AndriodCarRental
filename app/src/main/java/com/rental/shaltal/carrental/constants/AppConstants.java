package com.rental.shaltal.carrental.constants;

public final class AppConstants {

    //    General Messages Constants
    public static final String FAILED_LOGIN_MESSAGE = "Login Failed\nIncorrect Email or Password";


    //    Shared Preferences Constants
    public static final String SHARED_PREF = "SHARED_PREF";
    public static final String SAVED_EMAIL = "SAVED_EMAIL";
    public static final String SAVED_PASS = "SAVED_PASS";
    public static final String LOGIN_FLAG = "LOGIN_FLAG";


    //    Register Error messages
    public static final String REG_ERR_INVALID_NAME = "Entered Name is invalid";
    public static final String REG_ERR_EMPTY_GENDER = "Must select gender";
    public static final String REG_ERR_INVALID_EMAIL = "Entered email is invalid";
    public static final String REG_ERR_INVALID_PASS = "Entered password is invalid";
    public static final String REG_ERR_MATCH_PASS = "Passwords don't match";
    public static final String REG_ERR_PASS_REQ = "";
    public static final String REG_ERR_PHONE_NUMBER = "Entered number is incorrect";
    public static final String REG_ERR_EMPTY_COUNTRY = "Must select country";
    public static final String REG_ERR_EMPTY_CITY = "Must select city";


//    Validator Regex

    public static final String VAL_REG_EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static final String VAL_REG_NAME = "^[\\p{L}\\s'.-]{3,}+$";
    public static final String VAL_REG_PASS = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String VAL_REG_PHONE = "(\\+?( |-|\\.)?\\d{1,2}( |-|\\.)?)?(\\(?\\d{3}\\)?|\\d{3})( |-|\\.)?(\\d{3}( |-|\\.)?\\d{4})";
}
