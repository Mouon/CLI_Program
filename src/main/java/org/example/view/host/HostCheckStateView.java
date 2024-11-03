package org.example.view.host;

import org.example.domain.Book;
import org.example.dto.Model;
import org.example.service.host.HostCheckStateService;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.util.List;
import java.util.Scanner;

import static java.lang.Math.min;

public class HostCheckStateView implements CustomView{

    public ValidationService validationService;
    public HostCheckStateService hostCheckStateService;

    public HostCheckStateView(ValidationService validationService, HostCheckStateService hostCheckStateService) {
        this.validationService = validationService;
        this.hostCheckStateService = hostCheckStateService;
    }

    @Override
    public Model begin(Model model) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("상태를 확인할 도서를 입력하세요\n" +
                "(뒤로 가려면 x키를 입력하세요)");
        String bookName;
        while(true){
            System.out.print(">>>");
            String input = scanner.nextLine().trim();
            if(input.equals("X") || input.equals("x")){
                return new Model("/host/managebook", null);
            }
            input = validationService.BookNameValidation(input);
            if(input.equals("false")){
                System.out.println("옳바르지 않은 입력입니다.");
            }
            else if(hostCheckStateService.getBookList(input).isEmpty()){
                System.out.println("프로그램에 등록되지 않은 도서명입니다.");
            }
            else{
                bookName = input;
                break;
            }
        }

        List<Book> booklist = hostCheckStateService.getBookList(bookName);
        int bookPerPage = 10; //한 페이지에 보이는 책 개수
        int pageNumber = 0; //현재 페이지 번호
        int firstPage = 0; //첫 페이지
        int lastPage = (booklist.size() - 1) / bookPerPage; //마지막 페이지
        System.out.println(firstPage + " " + lastPage);
        while(true){
            System.out.print("===== 도서 목록 =====\n" +
                    "ISBN / 도서명 / 저자 / 대출 중 여부\n\n");
            int firstNumber = pageNumber * bookPerPage + 1;
            int lastNumber = min(booklist.size(), (pageNumber + 1) * bookPerPage);
            for(int i=firstNumber;i<=lastNumber;i++) {
                int index = i - 1;
                System.out.println(i + ". " +
                        booklist.get(index).getISBN() + " / " +
                        booklist.get(index).getBookName() + " / " +
                        booklist.get(index).getAuthorName() + " / " +
                        booklist.get(index).getIsCheckout());
            }
            System.out.println("(" + (pageNumber + 1) + " 페이지 / " + (lastPage + 1) + " 페이지)");
            System.out.print("\n" +
                    "A. 다음 페이지\n" +
                    "B. 이전 페이지\n" +
                    "(뒤로 가려면 x키를 입력하세요)\n");
            while(true){
                System.out.print(">>>");
                String input = scanner.nextLine().trim();
                if(input.equals("X") || input.equals("x")){
                    return new Model("/host/managebook", null);
                }
                input = validationService.abInputValidation(input);
                if(input.equals("A")){ //다음 페이지
                    if(pageNumber + 1 <= lastPage){
                        pageNumber++;
                        break;
                    }
                    else{
                        System.out.println("다음 페이지가 없습니다.");
                    }
                }
                else if(input.equals("B")){ //이전 페이지
                    if(pageNumber - 1 >= firstPage){
                        pageNumber--;
                        break;
                    }
                    else{
                        System.out.println("이전 페이지가 없습니다.");
                    }
                }
                else{
                    System.out.println("올바르지 않은 입력입니다.");
                }
            }
        }
    }

    @Override
    public String getUri() {
        return "/host/managebook/checkstate";
    }
}
