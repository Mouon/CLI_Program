package org.example.view;

import org.example.dto.Model;

import java.util.Scanner;

public class UserLoginView implements CustomView{
    @Override
    public Model begin(Model model) {
        Scanner scan = new Scanner(System.in);
        System.out.print();
    }

    @Override
    public String getUri() {
        return "/login/user";
    }
}
