package org.example.view.user;

import org.example.domain.Book;
import org.example.dto.Model;
import org.example.service.ShowBookDetailService;
import org.example.service.host.HostCheckStateService;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.util.List;
import java.util.Scanner;

import static java.lang.Math.min;


public class UserCheckStateView implements CustomView {
    public ValidationService validationService;
    public HostCheckStateService hostCheckStateService;
    public ShowBookDetailService showBookDetailService;

    public UserCheckStateView(ValidationService validationService, HostCheckStateService hostCheckStateService, ShowBookDetailService showBookDetailService) {
        this.validationService = validationService;
        this.hostCheckStateService = hostCheckStateService;
        this.showBookDetailService = showBookDetailService;
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
            if (input.isEmpty()) {
                System.out.println("옳바르지 않은 입력입니다.");
                continue;
            }
            if(input.equals("X") || input.equals("x")){
                return new Model("/user", null);
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
        while(true){
            System.out.print("===== 도서 목록 =====\n" +
                    "도서명 / 저자 / 대출 중 여부 / ISBN / 삭제 여부\n\n");
            int firstNumber = pageNumber * bookPerPage + 1;
            int lastNumber = min(booklist.size(), (pageNumber + 1) * bookPerPage);
            for(int i=firstNumber;i<=lastNumber;i++) {
                int index = i - 1;
                Book currentBook = booklist.get(index);
                System.out.print(i + ". " +
                        currentBook.getBookName() + " / " +
                        currentBook.getAuthorName() + " / " +
                        currentBook.getIsCheckout() + " / " +
                        currentBook.getISBN() + " / ");
                if(currentBook.isDelete()) System.out.println("Y");
                else System.out.println("N");
            }
            System.out.println("(" + (pageNumber + 1) + " 페이지 / " + (lastPage + 1) + " 페이지)");
            System.out.print("\n" +
                    "A. 다음 페이지\n" +
                    "B. 이전 페이지\n" +
                    "상세 정보를 확인할 도서의 번호를 입력해주세요.\n" +
                    "(뒤로 가려면 x키를 입력하세요)\n");
            while(true){
                System.out.print(">>>");
                String input = scanner.nextLine().trim();
                if(input.equals("X") || input.equals("x")){
                    return new Model("/user", null);
                }
                Integer numberInput = validationService.numberInputValidation(input);
                if(numberInput != null){
                    //도서를 선택했을 때
                    if(firstNumber <= numberInput && numberInput <= lastNumber){
                        long currentBookID = booklist.get(numberInput - 1).getBookId();
                        showBookDetailService.showBookDetail(currentBookID);
                        System.out.println("\n(뒤로 가려면 x키를 입력하세요)");
                        while(true){
                            System.out.print(">>>");
                            String input2 = scanner.nextLine().trim();
                            if(input2.equals("X") || input2.equals("x")) {
                                break;
                            }
                            else{
                                System.out.println("올바르지 않은 입력입니다.");
                            }
                        }
                        break;
                    }
                    else{
                        System.out.println("해당되는 도서가 없습니다.");
                        continue;
                    }
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
        return "/user/checkstate";
    }
}
