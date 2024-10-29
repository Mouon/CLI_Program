package org.example.service;

import org.example.domain.User;
import org.example.file.UserFileManager;

import java.util.List;

public class LoginService {

    public UserFileManager userFileManager;

    public LoginService(UserFileManager userFileManager) {
        this.userFileManager = userFileManager;
    }

    /**
     * 아이디 중복확인
     */
    public boolean checkIdAlreadyExist(String id){
        List<User> userList = userFileManager.loadUserList();
        for (User value : userList) {
            if (value.getUserId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 회원가입
     */
    public void registerNewUser(User user){
        userFileManager.join(user);
    }
}
