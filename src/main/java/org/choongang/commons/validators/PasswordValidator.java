package org.choongang.commons.validators;

public interface PasswordValidator {


    default boolean alphaCheck(String password, boolean caseIncensitive){
        if(caseIncensitive){
            String pattern = ".*[a-zA-Z]+.*";

            return password.matches(pattern);
        } else{
            String pattern1 = ".*[a-z]+.*";
            String pattern2 = ".*[A-Z]+.*";


            return password.matches(pattern1) && password.matches(pattern2);
        }
    }

    default boolean numberCheck(String password){
        return password.matches(".*\\d+.*");
    }

    default boolean specialCharsCheck(String password) {
        String pattern = ".*[`~!@#$%^*&()-_+=]+.*";

        return password.matches(pattern);
    }

}
