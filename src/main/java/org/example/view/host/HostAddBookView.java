package org.example.view.host;

import org.example.domain.Author;
import org.example.domain.User;
import org.example.dto.LoginMember;
import org.example.dto.Model;
import org.example.service.book.BookManageService;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;
import org.example.file.AuthorFileManger;

import java.util.*;

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

        Author targetAuthor = null;
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
                            authorFileManger.addAuthor(new Author(authorId,divide[0].trim(),LoginMember.getLoginTime()));
                        }catch (NumberFormatException e){
                            System.out.println("#뒤에 따라오는 문자열은 숫자여야합니다.");
                            continue;
                        }
                    }else {//입력시 #미포함
                        List<Author> authorList=authorFileManger.loadAuthorList();
                        //기존 동명저자 수 확인
                        Map<String,List<Author>> authorMap = new HashMap<>();
                        for(Author author:authorList){
                            String name = author.getAuthorName();
                            authorMap.putIfAbsent(name, new ArrayList<>());
                            authorMap.get(name).add(author);
                        }

                        if(authorMap.containsKey(input)){//존재확인
                            List<Author> matchedAuthorList=authorMap.get(input);
                            if(matchedAuthorList.size()==1){//1명
                                if(sc.nextLine().trim().isEmpty()){
                                    targetAuthor = matchedAuthorList.get(0);
                                }
                            }else {//1명이상
                                System.out.println("저자 고유번호 / 저자명 / 생년월일");
                                for(Author author:matchedAuthorList){
                                    System.out.println(author.getAuthorId()+" / "+author.getAuthorName()+" / "+author.getBirthDate());
                                }
                                System.out.print("고유번호를 입력하세요(0 입력시 새 저자로 저장)>>>");

                                String authorIdInput = sc.nextLine().trim();
                                Label:
                                while(!authorIdInput.equals("0")){

                                    for(Author author:matchedAuthorList){
                                        if(author.getAuthorId()==Long.parseLong(authorIdInput)){
                                            targetAuthor = author;
                                            break Label;
                                        }
                                    }

                                    System.out.println("해당 고유번호가 없습니다.");
                                    System.out.print("고유번호를 입력하세요(0 입력시 새 저자로 저장)>>>");
                                    authorIdInput = sc.nextLine().trim();
                                }
                                if (authorIdInput.equals("0")) { //새 저자 생성
                                    authorFileManger.addAuthor(new Author(input, LoginMember.getLoginTime()));
                                }
                            }
                        }else{
                            authorFileManger.addAuthor(new Author(input,LoginMember.getLoginTime()));
                        }

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



        return new Model("/host/managebook",null);
    }

    @Override
    public String getUri() {
        return "/host/managebook/add";
    }
}
