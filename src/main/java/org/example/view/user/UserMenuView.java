package org.example.view.user;

import org.example.dto.Model;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.util.Scanner;

public class UserMenuView implements CustomView {
    public ValidationService validationService;

    public UserMenuView(ValidationService validationService) {
        this.validationService = validationService;
    }
    @Override
    public Model begin(Model model) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== 사용자 메뉴 =====");
        System.out.println("1. 도서 검색");
        System.out.println("2. 도서 반납");
        System.out.println("3. 도서 상태 확인");
        System.out.println("4. 마이페이지");
        System.out.println("5. 로그아웃");

        while(true){
            System.out.print(">>>");
            String input = sc.nextLine().trim();
            String validationResult = validationService.menuInputValidation(input);

            if(validationResult.equals("1")){
                return new Model("/user/searchandcheckout",null);
            }else if (validationResult.equals("2")){
                return new Model("/user/bookreturn",null);
            }else if (validationResult.equals("3")){
                return new Model("/user/checkstate",null);
            }else if (validationResult.equals("4")){
                return new Model("/user/mypage",null);
            }else if (validationResult.equals("5")){
                return new Model("/login/logout",null);
            }else{
                System.out.println("올바르지 않은 입력입니다.");
            }
        }
    }

    @Override
    public String getUri() {
        return "/user";
    }
}
