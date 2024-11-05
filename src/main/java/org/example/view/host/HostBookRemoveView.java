package org.example.view.host;

import org.example.domain.Book;
import org.example.dto.Model;
import org.example.file.BookFileManager;
import org.example.service.book.BookManageService;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.util.List;
import java.util.Scanner;

public class HostBookRemoveView implements CustomView {

    private ValidationService validationService;
    private BookManageService bookManageService;
    private BookFileManager bookFileManager;

    public HostBookRemoveView(ValidationService validationService,BookManageService bookManageService, BookFileManager bookFileManager) {
        this.validationService = validationService;
        this.bookManageService = bookManageService;
        this.bookFileManager = bookFileManager;
    }

    @Override
    public Model begin(Model model) {
        Scanner sc = new Scanner(System.in);
        System.out.println("삭제할 도서를 입력하세요");
        System.out.println("(뒤로 가려면 x키를 입력하세요)");
        System.out.print(">>>");

        String bookName = sc.nextLine().trim().replaceAll("\\s+"," ");

        if(bookName.equals("x")||bookName.equals("X")) {
            return new Model("/host/managebook",null);
        }

        while(validationService.BookNameValidation(bookName)==null) {
            System.out.println("올바르지 않은 입력입니다.");
            System.out.print(">>>");
            bookName=sc.nextLine().trim().replaceAll("\\s+"," ");
        }

        int page=0;
        String input;
        List<Book> booklist= bookFileManager.loadBookListByName(bookName);

        while(booklist.isEmpty()) {
            System.out.println("프로그램에 등록되지 않은 도서명입니다.");
            System.out.print(">>>");
            bookName=sc.nextLine().trim().replaceAll("\\s+"," ");

            while(validationService.BookNameValidation(bookName)==null) {
                System.out.println("올바르지 않은 입력입니다.");
                System.out.print(">>>");
                bookName=sc.nextLine().trim().replaceAll("\\s+"," ");
            }

            if (bookName.equals("x")||bookName.equals("X")) {
                return new Model("/host/managebook",null);
            }
            booklist= bookFileManager.loadBookListByName(bookName);
        }

        while(true){
            System.out.println("===== 도서 삭제 목록 =====");
            System.out.println("도서명 / 저자 / ISBN /대출 중 여부");

            if (booklist.size()<=10){
                int i=1;
                for (Book book : booklist){
                    System.out.println(i+". "+book.getBookName()+" / "+book.getAuthorName()+" / "+book.getISBN()+" / "+book.getIsCheckout());
                    i++;
                }
            }else {
                for (int i=1;i<11;i++){
                    if(booklist.size()-page*10<i){
                        break;
                    }
                    Book currntBook = booklist.get(i-1+page*10);
                    System.out.println(i+". "+currntBook.getBookName()+" / "+currntBook.getAuthorName()+" / "+currntBook.getISBN()+" / "+currntBook.getIsCheckout());
                }
            }

            if(booklist.size()%10==0) {
                System.out.println("("+(page+1)+" 페이지 / "+(booklist.size()/10)+" 페이지)");
            }else {
                System.out.println("("+(page+1)+" 페이지 / "+(booklist.size()/10+1)+" 페이지)");
            }
            System.out.println("도서를 삭제하려면 해당 도서의 번호를 입력하세요.");
            System.out.println("A. 다음 페이지");
            System.out.println("B. 이전 페이지");
            System.out.println("(뒤로 가려면 x키를 입력하세요)");
            System.out.print(">>>");

            input= sc.nextLine().trim();
            while(validationService.abInputValidation(input).equals("false")) {
                if(input.equals("x")||input.equals("X")) {
                    return new Model("/host/managebook/remove",null);
                }
                if(validationService.numberInputValidation(input)!=null) {break;}
                System.out.println("올바르지 않은 입력입니다.");
                System.out.print(">>>");
                input=sc.nextLine().trim();
            }

            if (validationService.abInputValidation(input).equals("A")) {

                if(booklist.size()%10==0) {
                    if(page==booklist.size()/10-1) {
                        System.out.println("다음 페이지가 없습니다.");
                    }else page++;
                }else {
                    if(page==booklist.size()/10) {
                        System.out.println("다음 페이지가 없습니다.");
                    }else page++;
                }

                continue;
            }else if (validationService.abInputValidation(input).equals("B")) {
                if(page==0)
                    System.out.println("이전 페이지가 없습니다.");
                else page--;
                continue;
            }

            if (Integer.parseInt(input)>=1 && Integer.parseInt(input)<=10) {
                System.out.println("정말로 삭제 하시겠습니까?[yes/no]");
                System.out.print(">>>");
                String yesno = sc.nextLine().trim();
                while (!validationService.ynInputValidation(yesno).equals("yes")&&!validationService.ynInputValidation(yesno).equals("no")) {
                    System.out.println("올바르지 않은 입력입니다.");
                    System.out.print(">>>");
                    yesno=sc.nextLine().trim();
                }
                if (validationService.ynInputValidation(yesno).equals("yes")) {
                    bookManageService.removeBook(booklist.get(Integer.parseInt(input)-1+page*10));
                    System.out.println("삭제되었습니다");
                    return new Model("/host/managebook/remove",null);
                }else if (validationService.ynInputValidation(yesno).equals("no")) {
                    System.out.println("취소되었습니다");
                    return new Model("/host/managebook/remove",null);
                }
            }
        }


    }

    @Override
    public String getUri() {
        return "/host/managebook/remove";
    }
}
