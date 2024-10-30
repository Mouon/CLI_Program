package org.example.controller;

import org.example.view.CustomView;

import java.util.List;

public class LoginController extends CustomController{

    public LoginController(List<CustomView> views) {
        super(views);
    }

    @Override
    String setDefaultUri() {
        return "/login";
    }
}
