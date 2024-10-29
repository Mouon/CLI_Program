package org.example.view;

import org.example.dto.Model;
import org.example.service.validater.ValidationService;

import java.util.Scanner;

/**
 * 예시 수정해서 작업 시작
 * */
public class HomeView implements CustomView{

    private ValidationService validationService;

    public HomeView(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Model begin(Model model) {
        Scanner scan = new Scanner(System.in);
        System.out.println("===== 도서 관리 프로그램 =====\n" +
                "1. 회원가입\n" +
                "2. 로그인\n" +
                "3. 종료");
        while(true){
            System.out.print(">>>");
            String input = scan.nextLine().trim();
            Integer choice = validationService.numberInputValidation(input);
            if(choice == null){
                System.out.println("올바르지 않은 입력입니다.");
                continue;
            }
            switch (choice){
                case 1:
                    return new Model("/register", null);
                case 2:
                    return new Model("/login",null);
                case 3:
                    System.out.println("프로그램을 종료합니다.");
                    scan.close();
                    System.exit(0);
                default:
                    System.out.println("올바르지 않은 입력입니다.");
            }
        }
    }

    @Override
    public String getUri() {
        return "/main";
    }
}
