package org.example.view;

import org.example.domain.User;
import org.example.dto.LoginMember;
import org.example.dto.Model;
import org.example.service.user.LoginService;
import org.example.service.validater.ValidationService;

import java.time.LocalDate;
import java.util.Scanner;

public class HostLoginView implements CustomView{

    public ValidationService validationService;
    public LoginService loginService;

    public HostLoginView(ValidationService validationService, LoginService loginService) {
        this.validationService = validationService;
        this.loginService = loginService;
    }

    @Override
    public Model begin(Model model) {
        String str=(String)model.getAttribute();
        Scanner scan = new Scanner(System.in);
        String id, password;
        LocalDate date = loginService.parseDate(str);

        while(true){
            System.out.print("아이디 >>>");
            id = scan.nextLine().trim();
            while (!validationService.idInputValidation(id)) {
                System.out.println("올바르지 않은 형식입니다.");
                System.out.print("아이디 >>>");
                id = scan.nextLine().trim();
            }

            System.out.print("비밀번호 >>>");
            password = scan.nextLine().trim();
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
                return new Model("/host", null);
            }
        }
    }

    @Override
    public String getUri() {
        return "/login/host";
    }
}
