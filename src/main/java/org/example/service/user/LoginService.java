package org.example.service.user;

import org.example.domain.User;
import org.example.file.UserFileManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LoginService {
    public UserFileManager userFileManager;

    public LoginService(UserFileManager userFileManager) {
        this.userFileManager = userFileManager;
    }

    /**
     * id 값으로 User 객체 찾기
     */
    public User findUser(String id){
        User targetUser = userFileManager.loadUserById(id);
        return targetUser;
    }

    /**
     * 아이디, 비밀번호로 유저가 있는지 확인하기
     */
    public boolean userExists(String id, String password, String memberType){
        User loginUser = userFileManager.loadUserById(id);
        if(loginUser == null){
            return false; // 아이디 자체를 찾을 수 없는 경우
        }else {
            if (loginUser.getPassword().equals(password) && loginUser.getUserType().equals(memberType)) {
                return true; // 아이디, 비밀번호, 유저 타입(관리자) 전부 일치
            }else {
                return false; // 아이디는 있는데, 비밀번호가 틀린경우
            }
        }
    }

    /**
     * 다양한 형식으로 들어오는 날짜를 LocalDate 형식으로 반환
     */
    public LocalDate parseDate(String date) {
        // 다양한 날짜 형식에 대한 DateTimeFormatter를 준비합니다.
        String[] patterns={"yyyyMMdd","yyyy/MM/dd","yyyy.MM.dd","yyyy-MM-dd","yy.MM.dd","yyMMdd","yy-MM-dd","yy/MM/dd"};

        for(String pattern:patterns){
            try{
                DateTimeFormatter formatter=DateTimeFormatter.ofPattern(pattern);
                return LocalDate.parse(date,formatter);
            }catch(DateTimeParseException e){

            }
        }
        return null;
    }
}
