package com.example.adminmenu;

public  class Function {
    public boolean isNotStringInteger(String stringToCheck) {
        int radix = 10;
        for(int i = 0; i < stringToCheck.length(); i++) {
            if(i == 0 && stringToCheck.charAt(i) == '-') {     //Check for negative Integers
                return true;
            }
            if(Character.digit(stringToCheck.charAt(i),radix) < 0) return true;
        }
        return false;
    }

    public boolean isStringInteger(String stringToCheck) {
        int radix = 10;
        for(int i = 0; i < stringToCheck.length(); i++) {
            if(i == 0 && stringToCheck.charAt(i) == '-') {     //Check for negative Integers
                return false;
            }
            if(Character.digit(stringToCheck.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}
