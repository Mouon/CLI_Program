package org.example.controller;

import org.example.view.CustomView;
import java.util.List;

public class HomeController extends CustomController {

    public HomeController(List<CustomView> views) {
        super(views);
    }

    @Override
    String setDefaultUri() {
        return "/main";
    }
}