package com.example.bread;

/**
 * This is used for our JUnitTesting to validate transaction inuputs. They must be float/int values.
 * @Author Nick Ross
 * @version 2019.12
 */
public class ValueValidator {
    private boolean isValid = false;
    public static boolean isValidValue(String value){
        if(value == null){
            return false;
        }else{
            try{
                Float.parseFloat(value);
                return true;
            }catch(Exception e){
                return false;
            }
        }
    }
}
