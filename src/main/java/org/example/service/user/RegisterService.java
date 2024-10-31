package org.example.service.user;

import org.example.domain.User;
import org.example.file.UserFileManager;

import java.util.List;

public class RegisterService {

    public UserFileManager userFileManager;

    public RegisterService(UserFileManager userFileManager) {
        this.userFileManager = userFileManager;
    }

    public void userRegister(User user){
        userFileManager.join(user); // 설계서 join 으로 수정 필요
    }

    public void hostRegister(User host){
        userFileManager.join(host); // 여기도 join 으로 수정
    }

    public boolean UseridExists(String id){
        List<User> userList = userFileManager.loadUserList();
        for (User value : userList) {
            if (value.getUserId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
