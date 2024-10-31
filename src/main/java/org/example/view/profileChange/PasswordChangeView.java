package org.example.view.profileChange;

import org.example.dto.LoginMember;
import org.example.dto.Model;
import org.example.service.ProfileChangeService;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.util.Scanner;

public class PasswordChangeView implements CustomView {
    public ValidationService validationService;
    public ProfileChangeService profileChangeService;

    public PasswordChangeView(ValidationService validationService, ProfileChangeService profileChangeService){
        this.validationService = validationService;
        this.profileChangeService = profileChangeService;
    }

    @Override
    public Model begin(Model model) {
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("이전 비밀번호를 입력하세요.");
            System.out.println("(뒤로 가려면 x키를 입력하세요)");
            System.out.print(">>>");
            String input = sc.nextLine().trim();
            boolean pwValidationResult = validationService.pwInputValidation(input);
            String stringValidationResult = validationService.menuInputValidation(input);

            if(stringValidationResult.equals("X")){
                //뒤로가기 입력인 경우
                return new Model("/user/mypage",null);
            }
            //기존 비밀번호와 입력한 값이 일치하는지 확인
            if(LoginMember.getInstance().getPassword().equals(stringValidationResult)){
                String newPwInput;
                String confirmPwInput;
                while(true){
                    System.out.println("새로운 비밀번호를 입력하세요.");
                    System.out.println("(뒤로 가려면 x키를 입력하세요)");
                    System.out.print(">>>");
                    //2단계 입력
                    newPwInput = sc.nextLine().trim();
                    String stringValidationResult2 = validationService.menuInputValidation(newPwInput);
                    boolean pwValidationResult2 = validationService.pwInputValidation(newPwInput);

                    if(stringValidationResult2.equals("X")){
                        break;
                    }
                    if(pwValidationResult2){
                        newPwInput = stringValidationResult2;
                        while(true){
                            System.out.println("새로운 비밀번호를 다시 한번 입력하세요.");
                            System.out.println("(뒤로 가려면 x키를 입력하세요)");
                            System.out.print(">>>");
                            //3단계 입력
                            confirmPwInput = sc.nextLine().trim();
                            String stringValidationResult3 = validationService.menuInputValidation(confirmPwInput);
                            boolean pwValidationResult3 = validationService.pwInputValidation(confirmPwInput);

                            if(stringValidationResult3.equals("X")){
                                break;
                            }
                            if(pwValidationResult3){
                                confirmPwInput = stringValidationResult3;
                                if(confirmPwInput.equals(newPwInput)){
                                    //비밀번호 재설정
                                    profileChangeService.changePassword(confirmPwInput);
                                    //완료 메세지
                                    System.out.println("비밀번호가 수정되었습니다.");
                                    //userType에 따라 해당 mypage로 이동
                                    if(LoginMember.getInstance().getUserType().equals("사용자")){
                                        return new Model("/user/mypage",null);
                                    }else{
                                        return new Model("/host/mypage",null);
                                    }
                                }else{
                                    System.out.println("2단계 비밀번호와 일치하지 않습니다.");
                                }
                            }
                        }
                    }else{
                        System.out.println("올바르지 않은 입력입니다.");
                    }
                }
            }else{
                //1단계 입력 오류처리
                if(pwValidationResult){
                    System.out.println("기존 비밀번호와 일치하지 않습니다.");
                }else{
                    System.out.println("올바르지 않은 입력입니다.");
                }
            }
        }
    }

    @Override
    public String getUri() {
        return "profilechange/passwordchange";
    }
}
