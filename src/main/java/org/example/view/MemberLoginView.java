package org.example.view;

import org.example.dto.Model;
import org.example.service.validater.ValidationService;

import java.util.Scanner;

public class MemberLoginView implements CustomView{

    public ValidationService validationService;

    public MemberLoginView(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Model begin(Model model) {
        Scanner scan = new Scanner(System.in);
        System.out.println("====== 로그인 ======\n" +
                "1. 사용자 로그인\n" +
                "2. 관리자 로그인\n" +
                "(뒤로 가려면 x 를 입력하세요.)");
        while(true){
            System.out.print(">>>");
            String input = scan.nextLine();
            String choice = validationService.menuInputValidation(input);
            switch (choice){
                case "1":
                    System.out.println("====== 사용자 로그인 ======");
                    return new Model("/login/userdate",null);
                case "2":
                    System.out.println("====== 관리자 로그인 ======");
                    return new Model("/login/hostdate",null);
                case "X":
                    return new Model("/main", null);
                default:
                    System.out.println("올바르지 않은 입력입니다.");
            }
        }
    }

    @Override
    public String getUri() {
        return "/login";
    }
}
