package org.example.view;

import org.example.domain.User;
import org.example.dto.Model;
import org.example.service.RegisterService;
import org.example.service.validater.ValidationService;

import java.util.Scanner;

public class UserRegisterView implements CustomView{

    private ValidationService validationService;
    private RegisterService registerService;

    public UserRegisterView(ValidationService validationService, RegisterService registerService) {
        this.validationService = validationService;
        this.registerService = registerService;
    }

    @Override
    public Model begin(Model model) {
        Scanner scan = new Scanner(System.in);
        String id, password, name;

        System.out.print("아이디 >>>");
        id = scan.nextLine();
        while (!validationService.idInputValidation(id)) {
            System.out.println("올바르지 않은 입력입니다.");
            System.out.print("아이디 >>>");
            id = scan.nextLine();
        }

        //아이디에 중복되는 값이 있는지 확인
        if (registerService.UseridExists(id)) {
            System.out.println("이미 존재하는 아이디입니다.");
            System.out.print("아이디 >>>");
            id = scan.nextLine();
        }

        System.out.print("비밀번호 >>>");
        password = scan.nextLine();
        while (!validationService.pwInputValidation(password)) {
            System.out.println("올바르지 않은 입력입니다.");
            System.out.print("비밀번호 >>>");
            password = scan.nextLine();
        }
        System.out.print("이름 >>>");
        name = scan.nextLine();
        while (!validationService.nameValidation(name)) {
            System.out.println("올바르지 않은 입력입니다.");
            System.out.print("이름 >>>");
            name = scan.nextLine();
        }

        User user = new User("사용자", id, password, name);
        registerService.userRegister(user);
        System.out.println("====== 회원가입 성공 ======");
        return new Model("/main", null);
    }

    @Override
    public String getUri() {
        return "/register/user";
    }
}
