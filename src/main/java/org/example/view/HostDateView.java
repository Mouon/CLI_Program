package org.example.view;

import org.example.dto.Model;

import java.util.Scanner;

public class HostDateView implements CustomView{
    @Override
    public Model begin(Model model) {
        Scanner scan = new Scanner(System.in);
        System.out.println("날짜를 입력하세요.\n(뒤로 가려면 x를 입력하세요.)");
        while(true){
            System.out.print(">>>");
            String input = scan.nextLine();
            if(){
                return new Model("/login/host", input);
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
