package com.example.bread;

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
