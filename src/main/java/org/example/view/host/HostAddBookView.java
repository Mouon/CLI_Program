package org.example.view.host;

import org.example.domain.Author;
import org.example.domain.AuthorBook;
import org.example.domain.Book;
import org.example.dto.LoginMember;
import org.example.dto.Model;
import org.example.file.AuthorBookFileManager;
import org.example.service.book.BookManageService;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;
import org.example.file.AuthorFileManger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
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

        List<Author> authorsToSave = new ArrayList<>();
        Author targetAuthor = null;

        Book targetBook = null;
        boolean authorGenerateFlag = true;
        boolean newISBNFlag = true;

        total:
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

                    Path path=Paths.get("src/main/resources/books.txt");
                    try(BufferedReader br=Files.newBufferedReader(path)){
                        while(true){//isbn중복여부 확인
                            String line=br.readLine();
                            if(line==null){
                                break;
                            }
                            String[] divide=line.split("\t");
                            if(input.equals(divide[5])){
                                System.out.println("이미 존재한 ISBN 입니다.");
                                System.out.println("ISBN / 도서명 / 출판사 / 출판연도");
                                System.out.println(divide[5]+" / "+divide[1] +" / "+ divide[2] +" / "+divide[3]);

                                System.out.println("같은 책을 추가하시겠습니까?(y/n)");
                                String yn= sc.nextLine().trim();
                                while(validationService.ynInputValidation(yn).equals("false")){
                                    System.out.println("옳바르지 않는 입력입니다.");
                                    yn= sc.nextLine().trim();
                                }
                                if(validationService.ynInputValidation(yn).equals("yes")){
                                    targetBook=new Book(divide[1],divide[2],divide[3],divide[4],divide[5],validationService.dateInputValidation(divide[6]));
                                    AuthorBookFileManager authorBookFileManager= new AuthorBookFileManager();

                                    List<AuthorBook> authorBooks = authorBookFileManager.loadByBookId(Long.parseLong(divide[0]));

//                                    targetAuthor = authorFileManger.loadAuthorById(authorBooks.get(0).getAuthorId());
                                    for(AuthorBook authorBook:authorBooks){
                                        authorsToSave.add(authorFileManger.loadAuthorById(authorBook.getAuthorId()));
                                    }

                                    index=5;
                                    authorGenerateFlag=false;
                                    newISBNFlag=false;
                                    continue total;
                                }else {
                                    continue total;
                                }
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    if (xJudge.equals("x")||xJudge.equals("X")){
                        return new Model("/host/managebook",null);
                    }else if (validationService.isbnInputValidation(dataList.get(index))==null) {
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
                        System.out.println(index);
                        authorGenerateFlag=false;
                    }

                    if (xJudge.equals("x")||xJudge.equals("X")){
                        authorsToSave.clear();
                        authorGenerateFlag=false;
                        index=index-2;
                    }
                    if(input.contains("#")){//입력에 #포함시
                        String[] divide = input.split("#");
                        if(divide.length!=2){
                            System.out.println("올바르지 않는 입력형식입니다.");
                            continue;
                        }
                        try{//저자생성or기존저자 불러오기
                            long authorId=Long.parseLong(divide[1]);

                            if(authorFileManger.loadAuthorById(authorId)!=null){
                                if(authorFileManger.loadAuthorById(authorId).getAuthorName().equals(divide[0].trim())){
                                    targetAuthor=authorFileManger.loadAuthorById(authorId);

                                    authorsToSave.add(targetAuthor);

                                    System.out.println("저자를 더 입력하겠습니까?(y/n)");
                                    String yn= sc.nextLine().trim();
                                    while(validationService.ynInputValidation(yn).equals("false")){
                                        System.out.println("옳바르지 않는 입력입니다.");
                                        yn= sc.nextLine().trim();
                                    }

                                    if(validationService.ynInputValidation(yn).equals("yes")){
                                        continue;
                                    }else {
                                        index++;
                                        continue;
                                    }

                                }else {
                                    System.out.println("기존 저자id에 저장된 데이터와 다릅니다.");
                                    continue;
                                }
                            }

                            targetAuthor=new Author(authorId,divide[0].trim(),LoginMember.getLoginTime());
                            authorGenerateFlag=true;

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
                                System.out.println("저자 고유번호 / 저자명 / 생년월일");
                                System.out.println(matchedAuthorList.get(0).getAuthorId()+" / "+matchedAuthorList.get(0).getAuthorName()+" / "+matchedAuthorList.get(0).getBirthDate());
                                System.out.print("엔터입력:동일인물, 기타입력: 새로생성\n>>>");


                                if(sc.nextLine().trim().isEmpty()){
                                    targetAuthor = matchedAuthorList.get(0);
                                    authorGenerateFlag = false;
                                }else {
                                    targetAuthor=new Author(input,LoginMember.getLoginTime());
                                    authorGenerateFlag = true;
                                }
                            }else if(matchedAuthorList.size()>1){//2명이상
                                System.out.println("저자 고유번호 / 저자명 / 생년월일");
                                for(Author author:matchedAuthorList){
                                    System.out.println(author.getAuthorId()+" / "+author.getAuthorName()+" / "+author.getBirthDate());
                                }
                                System.out.print("고유번호를 입력하세요(0 입력시 새 저자로 저장)\n>>>");

                                String authorIdInput = sc.nextLine().trim();
                                Label:
                                while(!authorIdInput.equals("0")){

                                    for(Author author:matchedAuthorList){
                                        if(author.getAuthorId()==Long.parseLong(authorIdInput)){
                                            targetAuthor = author;
                                            authorGenerateFlag=false;
                                            break Label;
                                        }
                                    }

                                    System.out.println("해당 고유번호가 없습니다.");
                                    System.out.print("고유번호를 입력하세요(0 입력시 새 저자로 저장)\n>>>");
                                    authorIdInput = sc.nextLine().trim();
                                }
                                if (authorIdInput.equals("0")) { //새 저자 생성
                                    targetAuthor=new Author(input,LoginMember.getLoginTime());
                                    authorGenerateFlag = true;
                                }
                            }else {
                                targetAuthor=new Author(input,LoginMember.getLoginTime());
                                authorGenerateFlag = true;
                            }

                        }else{
                            if(index==2) {
                                targetAuthor = new Author(input, LoginMember.getLoginTime());
                                authorGenerateFlag = true;
                            }
                        }

                    }

                    if(authorGenerateFlag){
                        while(true){
                            System.out.print("저자의 생년월일을 입력하세요\n>>>");
                            String date = sc.nextLine().trim();
                            if(validationService.dateInputValidation(date)!=null){
                                targetAuthor.setBirthDate(validationService.dateInputValidation(date));
                                break;
                            }
                            System.out.print("옳바르지 않는 입력 입니다.\n");
                        }
                    }

                    if(index==2){
                        authorsToSave.add(targetAuthor);
                    }

                    System.out.println("저자를 더 입력하겠습니까?(y/n)");
                    String yn= sc.nextLine().trim();
                    while(validationService.ynInputValidation(yn).equals("false")){
                        System.out.println("옳바르지 않는 입력입니다.");
                        yn= sc.nextLine().trim();
                    }

                    if(validationService.ynInputValidation(yn).equals("yes")){
                        index=1;//뒤에 index++존재해서
                    }

                }
                case 3->{//출판사
                    if (xJudge.equals("x")||xJudge.equals("X")){

                        authorsToSave.clear();//저자리스트 비우기

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
                        if(newISBNFlag){
                            index=index-2;//이전 단계로 이동
                        }else {
                            index=0; //ISBN에서 직접 넘어오면 뒤로가기 때 돌아가기
                            authorsToSave.clear();
                            continue ;
                        }
                    } else if (validationService.numberInputValidation(dataList.get(index))==null) {
                        System.out.println("올바르지 않는 입력입니다.");
                        index--;//재입력
                    }
                }
            }
            index++;
        }

        if(authorGenerateFlag){
            for (Author author : authorsToSave) {
                authorFileManger.addAuthor(author);
                Path path=Paths.get("src/main/resources/author.txt");
                try(BufferedReader br=Files.newBufferedReader(path)){
                    String lastLine =null;
                    String line;
                    while((line=br.readLine())!=null){
                        lastLine=line;
                    }
                    if(lastLine!=null){
                        String[] res = lastLine.split("\t");
                        author.setAuthorId(Long.parseLong(res[0]));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
//        System.out.println(targetAuthor.getAuthorId()+" / "+targetAuthor.getAuthorName()+" / "+targetAuthor.getBirthDate());

        if(newISBNFlag) {
            bookManageService.addBook(dataList.get(1), dataList.get(3), dataList.get(4), Integer.parseInt(dataList.get(5)), dataList.get(0), LoginMember.getLoginTime(), authorsToSave);
        }else {
            bookManageService.addBook(targetBook.getBookName(),targetBook.getPublishingHouse(),targetBook.getPublishingYear(),Integer.parseInt(dataList.get(5)),targetBook.getISBN(),LoginMember.getLoginTime(),authorsToSave);
        }


        return new Model("/host/managebook",null);
    }

    @Override
    public String getUri() {
        return "/host/managebook/add";
    }
}
