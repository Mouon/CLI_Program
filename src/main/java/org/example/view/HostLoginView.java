package org.example.view;

import org.example.dto.Model;

public class HostLoginView implements CustomView{
    @Override
    public Model begin(Model model) {
        return null;
    }

    @Override
    public String getUri() {
        return "/login/host";
    }
}
