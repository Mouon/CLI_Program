package org.example.dto;

import java.lang.reflect.Member;
import java.time.LocalDate;

public class LoginMember {
    private static Member member;
    private static LocalDate time;

    public static Member getInstance() {
        if (member == null) {
            throw new IllegalStateException("Member not registered");
        }
        return member;
    }

    public static LocalDate getTime() {
        return time;
    }

    public static void setMember(Member memberIn, LocalDate timeIn) {
        member = memberIn;
        time = timeIn;
    }
}
