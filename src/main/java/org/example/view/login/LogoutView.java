package org.example.view.login;

import org.example.dto.Model;
import org.example.view.CustomView;

public class LogoutView implements CustomView {
    @Override
    public Model begin(Model model) {
        System.out.println("====== 로그아웃 되었습니다. ======");

        return new Model("/main", null);
    }

    @Override
    public String getUri() {
        return "/login/logout";
    }
}
