package org.example.controller;

import org.example.view.CustomView;

import java.util.List;

public class ProfileChangeController extends CustomController{

    public ProfileChangeController(List<CustomView> views) {
        super(views);
    }

    @Override
    String setDefaultUri() {
        return "/profilechange";
    }
}
