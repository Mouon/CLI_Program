package org.example.view.host;

import org.example.dto.Model;
import org.example.service.book.BookManageServive;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HostAddBookView implements CustomView {

    private ValidationService validationService;
    private BookManageServive bookManageServive;

    public HostAddBookView(ValidationService validationService, BookManageServive bookManageServive) {
        this.validationService = validationService;
        this.bookManageServive = bookManageServive;
    }

    @Override
    public Model begin(Model model) {
        Scanner sc = new Scanner(System.in);
        List<String> dataList= new ArrayList<>(List.of("_", "_", "_", "_", "_", "_"));
        int index=0;
        List<String> dataName= List.of("도서명","저자명","출판사","출판연도","ISBN","수량");
        //0:도서명 1:저자명 2:출판사 3:출판연도 4:ISBN 5:수량
        while(true){
            if (index==6) break;
            System.out.println(dataName.get(index)+"을/를 입력하세요");
            System.out.println("(뒤로 가려면 x를 입력하세요)");
            System.out.print(">>>");

            dataList.set(index, sc.nextLine().trim());
            switch(index){//입력단계구분
                case 0->{//도서명
                    if (validationService.BookNameValidation(dataList.get(index)).equals("false")){
                        return new Model("/host/managebook",null);
                    }
                }
                case 1->{//저자명
                    if (dataList.get(index).equals("x")||dataList.get(index).equals("X")){
                        index=index-2;//이전 단계로 이동
                    }else if (validationService.authorInputValidation(dataList.get(index)).equals("false")){
                        System.out.println("옳바르지 않는 입력입니다.");
                        index--;//재입력
                    }
                }
                case 2->{//출판사
                    if (dataList.get(index).equals("x")||dataList.get(index).equals("X")){
                        index=index-2;//이전 단계로 이동
                    }else if (validationService.publisherInputValidation(dataList.get(index)).equals("false")){
                        System.out.println("옳바르지 않는 입력입니다.");
                        index--;//재입력
                    }
                }
                case 3,5->{//출판연도,수량
                    if (dataList.get(index).equals("x")||dataList.get(index).equals("X")){
                        index=index-2;//이전 단계로 이동
                    } else if (validationService.numberInputValidation(dataList.get(index))==null) {
                        System.out.println("옳바르지 않는 입력입니다.");
                        index--;//재입력
                    }
                }
                case 4->{//ISBN
                    if (dataList.get(index).equals("x")||dataList.get(index).equals("X")){
                        index=index-2;//이전 단계로 이동
                    } else if (validationService.authorInputValidation(dataList.get(index))==null) {//isbnValidation생기면 고칠예정
                        System.out.println("옳바르지 않는 입력입니다.");
                        index--;//재입력
                    }
                }
            }
            index++;
        }
        return new Model("/host/managebook",null);
    }

    @Override
    public String getUri() {
        return "/host/managebook/add";
    }
}
