package org.example.view;

import org.example.domain.User;
import org.example.dto.Model;
import java.util.Scanner;

public class UserRegisterView implements CustomView{
    @Override
    public Model begin(Model model) {
        Scanner scan = new Scanner(System.in);
        String id, password, name;

        System.out.print("아이디 >>>");
        id = scan.nextLine();
        while (!CustomValidation.idInputValidation(id)) {
            System.out.println("올바르지 않은 입력입니다.");
            System.out.print("아이디 >>>");
            id = scan.nextLine();
        }
        //아이디에 중복되는 값이 있는지 확인
        while (true) {
            if (registerService.UseridExists(id)) {
                System.out.println("이미 존재하는 아이디입니다.");
                System.out.print("아이디 >>>");
                id = scan.nextLine();
            }
        }

        System.out.print("비밀번호 >>>");
        password = scan.nextLine();
        while (!CustomValidation.pwInputValidation(password)) {
            System.out.println("올바르지 않은 입력입니다.");
            System.out.print("비밀번호 >>>");
            password = scan.nextLine();
        }
        System.out.print("이름 >>>");
        name = scan.nextLine();
        while (!CustomValidation.nameInputValidation(name)) {
            System.out.println("올바르지 않은 입력입니다.");
            System.out.print("이름 >>>");
            name = scan.nextLine();
        }

        User user = new User("사용자", id, password, name);
        // 저장

        System.out.println("====== 회원가입 성공 ======");
        return new Model("/main", null); //초기화면으로 돌아감 ,,
    }

    @Override
    public String getUri() {
        return "/register/user";
    }
}
