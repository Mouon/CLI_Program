package org.example.service.validater;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationService {
    private final static String REGEXP_ID_INPUT = "^[a-zA-Z0-9]{2,10}$";
    private final static String REGEXP_PW_INPUT = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@$!%*#?&])[A-Za-z[0-9]!@#$%^&*()-_+=]{8,}$";

    /**
     * ID 입력 규칙
     * REGEXP_ID_INPUT
     */
    public boolean idInputValidation(String input) {
        Pattern pattern = Pattern.compile(REGEXP_ID_INPUT);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * 비밀번호 입력 규칙
     * REGEXP_PW_INPUT
     */
    public boolean pwInputValidation(String input) {
        Pattern pattern = Pattern.compile(REGEXP_PW_INPUT);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
