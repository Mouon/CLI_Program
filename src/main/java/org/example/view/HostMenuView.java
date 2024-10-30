package org.example.view;

import org.example.dto.Model;

public class HostMenuView implements CustomView{
    @Override
    public Model begin(Model model) {
        System.out.println("host 메뉴 진입");
        System.exit(0);
        return null;
    }

    @Override
    public String getUri() {
        return "/host";
    }
}
