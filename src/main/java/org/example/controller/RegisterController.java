package org.example.controller;

import org.example.view.CustomView;

import java.util.List;

public class RegisterController extends CustomController{

    public RegisterController(List<CustomView> views) {
        super(views);
    }

    @Override
    String setDefaultUri() {
        return "/register";
    }
}
