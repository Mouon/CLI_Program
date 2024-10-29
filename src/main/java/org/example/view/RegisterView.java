package org.example.view;

import org.example.dto.Model;
import org.example.service.validater.ValidationService;

import java.util.Scanner;

public class RegisterView implements CustomView{

    public ValidationService validationService;

    public RegisterView(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Model begin(Model model) {
        Scanner scan = new Scanner(System.in);
        System.out.println("===== 회원가입 =====\n" +
                "1. 사용자 회원가입\n" +
                "2. 관리자 회원가입\n" +
                "(뒤로 가고 싶으면 x 를 입력하세요)\n");

        while(true){
            System.out.print(">>>");
            String input = scan.nextLine();
            String choice = String.valueOf(validationService.numberInputValidation(input));

            if(choice==null){
                System.out.println("올바르지 않은 입력입니다.");
                continue;
            }

            switch (choice){
                case "1":
                    System.out.println("==== 사용자 회원가입 ====");
                    return new Model("/login/register/user",null);
                case "2":
                    System.out.println("==== 관리자 회원가입 ====");
                    return new Model("login/register/host",null);
                default:
                    System.out.println("올바르지 않은 입력입니다.");
            }
            return null;
        }
    }

    @Override
    public String getUri() {
        return "/register";
    }
}
