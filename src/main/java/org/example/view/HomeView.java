package org.example.view;

import org.example.dto.Model;

/**
 * 예시 수정해서 작업 시작
 * */
public class HomeView implements CustomView{

    @Override
    public Model begin(Model model) {
        System.out.println("전기프 2 도서관 프로그램의 시작점 입니다. 위치 : view.HomeView ");
        return new Model("",null);
    }

    @Override
    public String getUri() {
        return "/main";
    }
}
