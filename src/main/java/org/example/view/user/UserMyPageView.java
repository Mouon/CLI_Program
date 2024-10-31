package org.example.view.user;

import org.example.dto.Model;
import org.example.view.CustomView;

import java.util.Scanner;

public class UserMyPageView implements CustomView {
    Scanner sc = new Scanner(System.in);

    @Override
    public Model begin(Model model) {
        System.out.println("===== 마이페이지 =====");
        System.out.println("이름 : 홍길동");//임시 코드
//        System.out.println("이름 : "+LoginMember.getInstance().getName());
        System.out.println("아이디 : hong123 "); //임시 코드
//        System.out.println("비밀번호 : "+LoginMember.getInstance().getId());
        System.out.println("==================");
        System.out.println("1. 비밀번호 수정");
        System.out.println("2. 도서 대출 내역 보기");
        System.out.println("뒤로 돌아가려면 x키를 입력하세요. ");

        while(true){
            System.out.print(">>> ");
            String input = sc.nextLine().trim();
            int mode = Integer.parseInt(input);

            if(mode == 1){
                return new Model("/personalinfo/passwordchange",null);
            } else if(mode == 2){
                return new Model("/user/mypage/checkouthistory",null);
            } else if(input.equals("x")){//이후에 validation 관련해서 수정할 예정
                break;
            } else{
                System.out.println("올바르지 않은 입력입니다.");
            }
        }
        return new Model("/user",null);
    }

    @Override
    public String getUri() {
        return "/user/mypage";
    }
}
