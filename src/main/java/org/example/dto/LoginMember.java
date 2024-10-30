package org.example.dto;

import org.example.domain.User;

import java.time.LocalDate;

public class LoginMember {
    private static User user;
    private static LocalDate loginTime;

    public static User getInstance() {
        if (user == null) {
            throw new IllegalStateException("Member not registered");
        }
        return user;
    }

    public static LocalDate getLoginTime() {
        return loginTime;
    }

    public static void setUser(User userIn, LocalDate timeIn) {
        user = userIn;
        loginTime = timeIn;
    }
}
