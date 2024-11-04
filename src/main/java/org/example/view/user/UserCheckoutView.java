package org.example.view.user;

import org.example.dto.LoginMember;
import org.example.dto.Model;
import org.example.service.CheckoutService;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.util.List;
import java.util.Scanner;

public class UserCheckoutView implements CustomView {
    public ValidationService validationService;
    public CheckoutService checkoutService;

    public UserCheckoutView(ValidationService validationService, CheckoutService checkoutService) {
        this.validationService = validationService;
        this.checkoutService = checkoutService;
    }

    @Override
    public Model begin(Model model) {
        Scanner sc = new Scanner(System.in);

        System.out.println("========= 도서 대출 내역 =========");
        System.out.println("'"+ LoginMember.getInstance().getUserName()+"' 님의 도서 대출 내역입니다.\n");
        System.out.println("반납 완료 도서에 대해서는\n'제목/저자/ISBN/대출일/반납예정일/반납일/연체여부'순으로 출력됩니다.\n");
        System.out.println("대출 도서에 대해서는\n'제목/저자/ISBN/대출일/반납예정일/대출중'순으로 출력됩니다.\n");

        List<String> checkoutList = checkoutService.getCheckoutHistory(LoginMember.getInstance());
        //불러온 checkout들을 출력
        for(String checkout:checkoutList){
            System.out.println(checkout);
        }
        System.out.println();
        System.out.println("(뒤로 가려면 x키를 입력하세요)");
        System.out.println("=============================");
        while(true){
            System.out.print(">>>");
            String input = sc.nextLine().trim();
            if (validationService.menuInputValidation(input).equals("X")){
                return new Model("/user/mypage",null);
            }else{
                System.out.println("올바르지 않은 입력입니다.");
            }
        }
    }

    @Override
    public String getUri() {
        return "/user/mypage/checkouthistory";
    }
}
