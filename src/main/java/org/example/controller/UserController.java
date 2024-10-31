package org.example.controller;

import org.example.view.CustomView;

import java.util.List;

public class UserController extends CustomController {

    public UserController(List<CustomView> views) {
        super(views);
    }
    @Override
    String setDefaultUri() {
        return "/user";
    }
}
