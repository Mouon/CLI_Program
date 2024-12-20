package org.example.view.host;

import org.example.dto.Model;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.util.Scanner;

public class HostMenuView implements CustomView {
    public ValidationService validationService;

    public HostMenuView(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Model begin(Model model) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== 관리자 메뉴 =====");
        System.out.println("1. 도서 관리");
        System.out.println("2. 마이페이지");
        System.out.println("3. 설정 변경");
        System.out.println("4. 로그아웃");

        while(true){
            System.out.print(">>>");
            String input = sc.nextLine().trim();
            String validationResult = validationService.menuInputValidation(input);

            if(validationResult.equals("1")){
                return new Model("/host/managebook",null);
            }else if (validationResult.equals("2")){
                return new Model("/host/mypage",null);
            }else if (validationResult.equals("3")){
                return new Model("/host/managesettings",null);
            }else if (validationResult.equals("4")){
                return new Model("/login/logout",null);
            }else{
                System.out.println("올바르지 않은 입력입니다.");
            }
        }
    }

    @Override
    public String getUri() {
        return "/host";
    }
}
