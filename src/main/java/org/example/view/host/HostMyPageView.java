package org.example.view.host;

import org.example.dto.Model;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.util.Scanner;

public class HostMyPageView implements CustomView {
    public ValidationService validationService;

    public HostMyPageView(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Model begin(Model model) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 마이페이지 =====");
        System.out.println("이름 : 홍길동");//임시 코드
//        System.out.println("이름 : "+LoginMember.getInstance().getName());
        System.out.println("아이디 : hong123 "); //임시 코드
//        System.out.println("비밀번호 : "+LoginMember.getInstance().getId());
        System.out.println("==================");
        System.out.println("1. 비밀번호 수정");
        System.out.println("뒤로 돌아가려면 x키를 입력하세요. ");

        while(true){
            System.out.print(">>> ");
            String input = sc.nextLine().trim();
            String validationResult = validationService.menuInputValidation(input);

            if(validationResult.equals("false")){
                //입력규칙에 맞지 않는 경우
                System.out.println("올바르지 않은 입력입니다.");
            }else if(validationResult.equalsIgnoreCase("x")){
                //뒤로가기 입력인 경우
                return new Model("/host",null);
            }else{
                //숫자 입력인 경우
                if(validationResult.equals("1")){
                    return new Model("/personalinfo/passwordchange",null);
                }else{
                    //메뉴에 없는 번호 입력인 경우
                    System.out.println("올바르지 않은 입력입니다.");
                }
            }
        }
    }

    @Override
    public String getUri() {
        return "/host/mypage";
    }
}
