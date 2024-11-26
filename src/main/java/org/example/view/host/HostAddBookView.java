package org.example.view.host;

import org.example.domain.Author;
import org.example.domain.User;
import org.example.dto.LoginMember;
import org.example.dto.Model;
import org.example.service.book.BookManageService;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;
import org.example.file.AuthorFileManger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HostAddBookView implements CustomView {

    private ValidationService validationService;
    private BookManageService bookManageService;

    public HostAddBookView(ValidationService validationService, BookManageService bookManageService) {
        this.validationService = validationService;
        this.bookManageService = bookManageService;
    }

    @Override
    public Model begin(Model model) {
        Scanner sc = new Scanner(System.in);
        AuthorFileManger authorFileManger = new AuthorFileManger();

        List<String> dataList= new ArrayList<>(List.of("_", "_", "_", "_", "_", "_"));
        int index=0;
        List<String> dataName= List.of("ISBN","도서명","저자","출판사","출판연도","수량");
        //0:ISBN 1:도서명 2:저자 3:출판사 4:출판연도 5:수량
        while(true){
            if (index==6) break;
            System.out.println(dataName.get(index)+"을/를 입력하세요");
            System.out.println("(뒤로 가려면 x를 입력하세요)");
            System.out.print(">>>");


            String input = sc.nextLine().trim().replaceAll("\\s+"," ");

            dataList.set(index, input);

            String xJudge=dataList.get(index).trim().replaceAll("\\s+"," ");
            switch(index){//입력단계구분
                case 0->{//ISBN
                    if (xJudge.equals("x")||xJudge.equals("X")){
                        return new Model("/host/managebook",null);
                    } else if (validationService.isbnInputValidation(dataList.get(index))==null) {
                        System.out.println("올바르지 않는 입력입니다.");
                        index--;//재입력
                    }
                }
                case 1->{//도서명
                    if (validationService.BookNameValidation(dataList.get(index))==null){
                        System.out.println("올바르지 않는 입력입니다.");
                        index--;//재입력
                    }
                    if (xJudge.equals("x")||xJudge.equals("X")){
                        index=index-2;//이전 단계로 이동
                    }
                }
                case 2->{//저자
                    if(validationService.authorInputValidation(dataList.get(index))==null){
                        System.out.println("올바르지 않는 입력입니다.");
                        index--;
                    }

                    if (xJudge.equals("x")||xJudge.equals("X")){
                        index=index-2;
                    }
                    if(input.contains("#")){//입력에 #포함시
                        String[] divide = input.split("#");
                        if(divide.length!=2){
                            System.out.println("올바르지 않는 입력형식입니다.");
                            continue;
                        }
                        try{//저자생성
                            long authorId=Long.parseLong(divide[1]);
                            authorFileManger.addAuthor(new Author(authorId,divide[0],LoginMember.getLoginTime()));
                        }catch (NumberFormatException e){
                            System.out.println("#뒤에 따라오는 문자열은 숫자여야합니다.");
                            continue;
                        }
                    }else {//입력시 #미포함
                        authorFileManger.addAuthor(new Author(input,LoginMember.getLoginTime()));
                    }
                }
                case 3->{//출판사
                    if (xJudge.equals("x")||xJudge.equals("X")){
                        index=index-2;//이전 단계로 이동
                    }else if (validationService.publisherInputValidation(dataList.get(index))==null){
                        System.out.println("올바르지 않는 입력입니다.");
                        index--;//재입력
                    }
                }
                case 4->{//출판연도
                    if (xJudge.equals("x")||xJudge.equals("X")){
                        index=index-2;//이전 단계로 이동
                    } else if (validationService.numberInputValidation(dataList.get(index))==null || input.length()!=4) {
                        System.out.println("올바르지 않는 입력입니다.");
                        index--;//재입력
                    }
                }
                case 5->{//수량
                    if (xJudge.equals("x")||xJudge.equals("X")){
                        index=index-2;//이전 단계로 이동
                    } else if (validationService.numberInputValidation(dataList.get(index))==null) {
                        System.out.println("올바르지 않는 입력입니다.");
                        index--;//재입력
                    }
                }
            }
            index++;
        }

        bookManageService.addBook(dataList.get(1),dataList.get(2),dataList.get(3), Integer.parseInt(dataList.get(4)),dataList.get(0),LoginMember.getLoginTime());

//        authorFileManger.addAuthor(new Author("",birth));

        return new Model("/host/managebook",null);
    }

    @Override
    public String getUri() {
        return "/host/managebook/add";
    }
}
