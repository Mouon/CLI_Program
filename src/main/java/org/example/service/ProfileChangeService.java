package org.example.service;

import org.example.domain.User;
import org.example.dto.LoginMember;
import org.example.file.UserFileManager;

import java.lang.reflect.Member;

public class ProfileChangeService {
    public UserFileManager userFileManager;

    public ProfileChangeService(UserFileManager userFileManager) {
        this.userFileManager = userFileManager;
    }


    /**
     * 비밀번호 변경하기
     */
    public void changePassword(String newPassword){
        User loginMember = LoginMember.getInstance();
        User userWithNewPassword = new User(loginMember.getUserType(), loginMember.getUserId(), newPassword, loginMember.getUserName());
        userFileManager.updateUser(userWithNewPassword);
        LoginMember.getInstance().setPassword(newPassword);
        LoginMember.setUser(LoginMember.getInstance(),LoginMember.getLoginTime());
    }

}
