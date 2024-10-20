package org.example;

import org.example.controller.MainController;
import org.example.dto.Model;
import org.example.factory.MainFactory;

/**
 * @ 프로그램의 시작점
 * @ 모든 클래스들의 중심이 되도록 설계해야함
 * */
public class Main {
    public static void main(String[] args) {
        MainFactory mainFactory = new MainFactory();
        MainController mainController = mainFactory.mainController();
        mainController.init(new Model("/main", null)); // 초기화면으로 이동
    }
}