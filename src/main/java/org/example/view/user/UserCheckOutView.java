package org.example.view.user;

import org.example.dto.Model;
import org.example.view.CustomView;

public class UserCheckOutView implements CustomView {

    @Override
    public Model begin(Model model) {
        return null;
    }

    @Override
    public String getUri() {
        return "/user/mypage/checkouthistory";
    }
}
