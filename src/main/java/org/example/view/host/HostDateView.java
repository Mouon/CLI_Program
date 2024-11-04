package org.example.view.host;

import org.example.dto.Model;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.time.LocalDate;
import java.util.Scanner;

public class HostDateView implements CustomView {

    public ValidationService validationService;

    public HostDateView(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Model begin(Model model) {
        Scanner scan = new Scanner(System.in);
        System.out.println("날짜를 입력하세요.\n(뒤로 가려면 x를 입력하세요.)");
        while(true){
            System.out.print(">>>");
            String input = scan.nextLine().trim();
            if(input.equals("X") || input.equals("x")){
                return new Model("/login", null); // 뒤로 가기 들어오면 뒤로가기
            }

            LocalDate inputDate = validationService.dateInputValidation(input);
            if(inputDate != null){
                return new Model("/login/hostdate/host", inputDate);
            }else{
                System.out.println("올바르지 않은 날짜 형식 입니다.");
            }
        }
    }

    @Override
    public String getUri() {
        return "/login/hostdate";
    }
}
