package org.example.controller;

import org.example.view.CustomView;

import java.util.List;

public class HostController extends CustomController{

    public HostController(List<CustomView> views) {
        super(views);
    }

    @Override
    String setDefaultUri() {
        return "/host";
    }
}
