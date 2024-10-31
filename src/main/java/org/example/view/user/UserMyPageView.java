package org.example.view.user;

import org.example.dto.LoginMember;
import org.example.dto.Model;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.util.Scanner;

public class UserMyPageView implements CustomView {
    public ValidationService validationService;

    public UserMyPageView(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Model begin(Model model) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== 마이페이지 =====");
        System.out.println("이름 : "+ LoginMember.getInstance().getUserName());
        System.out.println("아이디 : "+LoginMember.getInstance().getUserId());
        System.out.println("==================");
        System.out.println("1. 비밀번호 수정");
        System.out.println("2. 도서 대출 내역 보기");
        System.out.println("뒤로 돌아가려면 x키를 입력하세요. ");


        while(true){
            System.out.print(">>> ");
            String input = sc.nextLine().trim();
            String validationResult = validationService.menuInputValidation(input);

            if(validationResult.equals("false")){
                //입력규칙에 맞지 않는 경우
                System.out.println("올바르지 않은 입력입니다.");
            }else if(validationResult.equals("X")){
                //뒤로가기 입력인 경우
                return new Model("/user",null);
            }else{
                //숫자 입력인 경우
                if(validationResult.equals("1")){
                    return new Model("/profilechange/passwordchange",null);
                }else if(validationResult.equals("2")){
                    return new Model("/user/mypage/checkouthistory",null);
                }else {
                    //메뉴에 없는 번호 입력인 경우
                    System.out.println("올바르지 않은 입력입니다.");
                }
            }
        }
    }

    @Override
    public String getUri() {
        return "/user/mypage";
    }
}
