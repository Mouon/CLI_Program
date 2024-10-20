package org.example.controller;


import org.example.dto.Model;

import java.util.List;

public class MainController {
    private List<CustomController> controllers;

    public MainController(List<CustomController> controllers) {
        this.controllers = controllers;
    }

    public void init(Model model) {
        // 프로세스는 uri가 아무것도 없을 때 종료된다.
        while (model.getUri().startsWith("/")) {
            for (CustomController controller : controllers) {
                if (model.getUri().startsWith(controller.getDefaultUri())) {
                    model = controller.init(model);
                }
            }
        }
    }

}
