package org.example.view;

import jdk.jshell.execution.LoaderDelegate;
import org.example.domain.User;
import org.example.dto.LoginMember;
import org.example.dto.Model;
import org.example.service.user.LoginService;
import org.example.service.validater.ValidationService;

import java.time.LocalDate;
import java.util.Scanner;

public class UserLoginView implements CustomView{

    public ValidationService validationService;
    public LoginService loginService;

    public UserLoginView(ValidationService validationService, LoginService loginService) {
        this.validationService = validationService;
        this.loginService = loginService;
    }

    @Override
    public Model begin(Model model) {
        String str = model.getAttribute().toString();
        Scanner scan = new Scanner(System.in);
        String id, password;
        LocalDate date = loginService.parseDate(str);
        System.out.println("메인화면으로 가려면 x 를 눌러주세요.");

        while(true){
            System.out.print("아이디 >>>");
            id = scan.nextLine().trim();
            if(id.equals("X")||id.equals("x")){
                return new Model("/main", null);
            }
            while (!validationService.idInputValidation(id)) {
                System.out.println("올바르지 않은 형식입니다.");
                System.out.print("아이디 >>>");
                id = scan.nextLine().trim();
            }

            System.out.print("비밀번호 >>>");
            password = scan.nextLine().trim();
            if(password.equals("X")||password.equals("x")){
                return new Model("/main", null);
            }
            while (!validationService.pwInputValidation(password)) {
                System.out.println("올바르지 않은 형식입니다.");
                System.out.print("비밀번호 >>>");
                password = scan.nextLine().trim();
            }

            if(!loginService.userExists(id,password)){
                System.out.println("ID 또는 비밀번호를 다시 확인해주세요.");
                continue;
            }else{
                User user = loginService.findUser(id);
                LoginMember.setUser(user, date); // 로그인 정보 넘겨주기
                return new Model("/user", null);
            }
        }

    }

    @Override
    public String getUri() {
        return "/login/user";
    }
}
