package org.example.view.host;

import org.example.dto.Model;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.util.Scanner;

public class HostManageSettingsView implements CustomView {
    public ValidationService validationService;

    public HostManageSettingsView(ValidationService validationService) {
        this.validationService = validationService;
    }


    @Override
    public Model begin(Model model) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 설정 변경 메뉴 =====");
        System.out.println("1. 블랙리스트 기간 변경");
        System.out.println("2. 도서 대출 가능 권수 변경");

        while(true){
            System.out.print(">>>");
            String input = sc.nextLine();
            String validationResult = validationService.menuInputValidation(input);

            //뒤로가기
            if (validationResult.equalsIgnoreCase("x")){
                return new Model("/host",null);
            }
            //메뉴 입력
            if(validationResult.equals("1")){
                return new Model("/host/managesettings/changeblacklistduration",null);
            }else if(validationResult.equals("2")){
                return new Model("/host/managesettings/changemaxcheckout",null);
            }else{
                System.out.println("올바르지 않은 입력입니다.");
            }
        }
    }

    @Override
    public String getUri() {
        return "/host/managesettings";
    }
}
