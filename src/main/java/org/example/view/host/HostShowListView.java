package org.example.view.host;

import org.example.domain.Book;
import org.example.dto.Model;
import org.example.service.host.HostShowListService;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.util.List;
import java.util.Scanner;

import static java.lang.Math.min;

public class HostShowListView implements CustomView {

    public ValidationService validationService;
    public HostShowListService hostShowListService;

    public HostShowListView(ValidationService validationService, HostShowListService hostShowListService) {
        this.validationService = validationService;
        this.hostShowListService = hostShowListService;
    }

    @Override
    public Model begin(Model model) {
        Scanner scanner = new Scanner(System.in);
        List<Book> booklist = hostShowListService.getBookList();
        int bookPerPage = 10; //한 페이지에 보이는 책 개수
        int pageNumber = 0; //현재 페이지 번호
        int firstPage = 0; //첫 페이지
        int lastPage = (booklist.size() - 1) / bookPerPage; //마지막 페이지
        System.out.println(firstPage + " " + lastPage);
        while(true){
            System.out.print("===== 도서 목록 =====\n" +
                    "ISBN / 제목 / 저자 / 출판사 / 출판연도\n\n");
            int firstNumber = pageNumber * bookPerPage + 1;
            int lastNumber = min(booklist.size(), (pageNumber + 1) * bookPerPage);
            for(int i=firstNumber;i<=lastNumber;i++){
                int index = i - 1;
                System.out.println(i + ". " +
                        booklist.get(index).getISBN() + " / " +
                        booklist.get(index).getBookName() + " / " +
                        booklist.get(index).getAuthorName() + " / " +
                        booklist.get(index).getPublishingHouse() + " / " +
                        booklist.get(index).getPublishingYear());
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
        return "/host/managebook/showlist";
    }
}
