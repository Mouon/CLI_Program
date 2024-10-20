package org.example.controller;

import org.example.dto.Model;
import org.example.view.CustomView;

import java.util.List;

public abstract class CustomController {
    private final List<CustomView> views;
    private String defaultUri = "";

    public CustomController(List<CustomView> views) {
        this.views = views;
        defaultUri = setDefaultUri();
    }

    abstract String setDefaultUri();

    public String getDefaultUri() {
        return defaultUri;
    }

    public Model init(Model model) {
        while (model.getUri().startsWith(defaultUri)) {
            // view 선택 로직 시작
            for (CustomView view : views) {
                if (view.getUri().equals(model.getUri())) {
                    model = view.begin(model);
                }
            }
            // view 선택 로직 종료
        }

        return model;
    }
}