package org.example.service.validater;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationService {
    private final static String REGEXP_ID_INPUT = "^[a-zA-Z0-9]{2,10}$";
    private final static String REGEXP_PW_INPUT = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@$!%*#?&])[A-Za-z[0-9]!@#$%^&*()-_+=]{8,}$";
    private static final String REGEXP_DATE_INPUT = "^\\s*(\\d{2}|\\d{4})([-/.]?)(0[1-9]|1[012])\\2(0[1-9]|[12]\\d|3[01])\\s*$";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * @class ValidationService
     * @description 숫자 입력 규칙
     * @return 규칙에 부합하면 해당 숫자(Integer), 않으면 NULL
     * */
    public Integer numberInputValidation(String input){
        try {
            input=input.trim();
            String[] str = input.split(" ");
            if(str.length>1){
                return null;
            }
            if(str[0].startsWith("00")){
                return null;
            }
            return Integer.parseInt(str[0]);
        }catch (Exception e){
            return null;
        }

    }

    /**
     * @class ValidationService
     * @description 날짜 입력 규칙
     * @return 규칙에 부합하면 해당 날짜(LocalDate), 않으면 NULL
     * */
    public LocalDate dateInputValidation(String input){
        try {
            Pattern pattern = Pattern.compile(REGEXP_DATE_INPUT);
            Matcher matcher = pattern.matcher(input);
            if(!matcher.matches()){
                return null;
            }
            input=input.trim();
            input = input.replaceAll("[-/.]", "");
            if (input.length() == 6) {
                input = "20" + input;
            }
            return LocalDate.parse(input, dateFormatter);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * @class ValidationService
     * @description ID 입력 규칙
     * @return boolean 값
     * REGEXP_ID_INPUT
     */
    public boolean idInputValidation(String input) {
        Pattern pattern = Pattern.compile(REGEXP_ID_INPUT);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * @class ValidationService
     * @description 비밀번호 입력 규칙
     * @return boolean 값
     * REGEXP_PW_INPUT
     */
    public boolean pwInputValidation(String input) {
        Pattern pattern = Pattern.compile(REGEXP_PW_INPUT);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * @class ValidationService
     * @description yes/no 입력 규칙
     * @return yes/no(String), 부합하지않을때 false(String)
     */
    public String ynInputValidation(String input){
        try {
            input=input.trim().toLowerCase();
            if (input.equals("yes")|| input.equals("y")) {
                return "yes";
            }else if( input.equals("no") || input.equals("n")){
                return "no";
            }
            return "false";
        }catch (Exception e){
            return "false";
        }
    }

    /**
     * @class ValidationService
     * @description A/B 입력 규칙
     * @return A/B(String), 부합하지않을때 false(String)
     */
    public String abInputValidation(String input){
        try {
            input=input.trim().toUpperCase();
            if (input.equals("A")||input.equals("B")) {
                return input;
            }else{
                return "false";
            }
        }catch (Exception e){
            return "false";
        }
    }

    /**
     * @class ValidationService
     * @description 메뉴 입력 규칙
     * @return_1 x관련 입력일때 : X(대문자),
     * @return_2 나머지 정상 입력 : 입력값(String),
     * @return_2 입력이 잘못되었을때 : false(String)
     */
    public String menuInputValidation(String input){
        try {
            input=input.trim();
            if (input.equals("x")||input.equals("X")) {
                return input.toUpperCase();
            }
            else{
                String[] str = input.split(" ");
                if(str.length>1){
                    return "false";
                }
                if(str[0].startsWith("00")){
                    return "false";
                }
                return String.valueOf(Integer.parseInt(str[0]));
            }
        }catch (Exception e){
            return "false";
        }
    }

}
