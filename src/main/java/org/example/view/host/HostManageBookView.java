package org.example.view.host;


import org.example.dto.Model;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.util.Scanner;

public class HostManageBookView implements CustomView {
    public ValidationService validationService;

    public HostManageBookView(ValidationService validationService) {this.validationService = validationService;}

    @Override
    public Model begin(Model model){
        Scanner sc = new Scanner(System.in);

        System.out.println("===== 도서 관리 =====");
        System.out.println("1.도서 등록");
        System.out.println("2.도서 목록 보기");
        System.out.println("3.도서 상태 확인");
        System.out.println("4.도서 삭제");
        System.out.println("(뒤로 가려면 x키를 입력하세요)");

        while (true){
            System.out.println(">>>");
            String input = sc.nextLine().trim();
            String ValidationResult = validationService.menuInputValidation(input);



            if(ValidationResult.equals("1")){
                return new Model("/host/managebook/add",null);
            }else if(ValidationResult.equals("2")){
                return new Model("/host/managebook/showlist",null);
            }else if(ValidationResult.equals("3")){
                return new Model("/host/managebook/checkstate",null);
            }else if(ValidationResult.equals("4")){
                return new Model("/host/managebook/remove",null);
            }else if(ValidationResult.equals("X")){
                return new Model("/host",null);
            }else {
                System.out.println("옳바르지 않는 입력입니다.");
            }
        }
    }

    @Override
    public String getUri() {
        return "/host/managebook";
    }

}
