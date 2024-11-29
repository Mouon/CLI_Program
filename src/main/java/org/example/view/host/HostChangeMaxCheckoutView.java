package org.example.view.host;

import org.example.dto.Model;
import org.example.service.SettingService;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.util.Scanner;

public class HostChangeMaxCheckoutView implements CustomView {
    public ValidationService validationService;
    public SettingService settingService;

    public HostChangeMaxCheckoutView(ValidationService validationService, SettingService settingService) {
        this.validationService = validationService;
        this.settingService = settingService;
    }

    @Override
    public Model begin(Model model) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 대출 가능 권수 변경 =====");
        System.out.println("가능한 최대 대출 권수를 변경합니다.");
        System.out.println("현재 최대 대출 권수 는 "+settingService.getMaxCheckout()+"권 입니다.");
        System.out.println("==================\n");
        System.out.println("변경할 최대 권수를 입력하세요.");
        System.out.println("(뒤로 돌아가려면 x키를 입력하세요.)");

        while(true){
            System.out.print(">>> ");
            String input = sc.nextLine().trim();

            if(validationService.menuInputValidation(input).equals("X")){
                //뒤로가기
                System.out.println("뒤로가기 실행");
                return new Model("/host/managesettings", null);
            }

            //뒤로가기가 아니고 유효한 입력인 경우 변경할 반납기간 값으로 설정
            Integer newMaxCheckout = validationService.numberInputValidation(input);

            //올바른 입력인 경우
            if(newMaxCheckout!= null && newMaxCheckout > 0){
                settingService.changeMaxCheckout(newMaxCheckout);
                System.out.println("최대 대출 권수가 "+newMaxCheckout+"권으로 변경되었습니다.");
                return new Model("/host/managesettings", null);
            }else{
                System.out.println("올바르지 않은 입력입니다.");
            }
        }
    }

    @Override
    public String getUri() {
        return "/host/managesettings/changemaxcheckout";
    }


}
