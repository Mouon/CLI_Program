package org.example.view;

import org.example.dto.Model;

public class UserMenuView implements CustomView{
    @Override
    public Model begin(Model model) {
        System.out.println("user 메뉴 출력 필요");
        System.exit(0);
        return null;
    }

    @Override
    public String getUri() {
        return "/user";
    }
}
