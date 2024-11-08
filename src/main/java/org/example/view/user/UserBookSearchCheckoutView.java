package org.example.view.user;

import org.example.domain.Checkout;
import org.example.dto.LoginMember;
import org.example.dto.Model;
import org.example.file.CheckoutFileManager;
import org.example.file.SettingFileManager;
import org.example.service.SettingService;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;
import org.example.domain.Book;
import org.example.file.BookFileManager;
import org.example.file.BlackListFileManager;

import java.util.List;
import java.util.Scanner;

public class UserBookSearchCheckoutView implements CustomView {
    public ValidationService validationService;
    public BookFileManager bookFileManager;
    public BlackListFileManager blackListFileManager;
    public CheckoutFileManager checkoutFileManager;
    public SettingService settingService;
    public Scanner sc = new Scanner(System.in);
    public UserBookSearchCheckoutView(ValidationService validationService, BookFileManager bookFileManager, BlackListFileManager blackListFileManager, CheckoutFileManager checkoutFileManager, SettingService settingService) {
        this.validationService = validationService;
        this.bookFileManager = bookFileManager;
        this.blackListFileManager = blackListFileManager;
        this.checkoutFileManager = checkoutFileManager;
        this.settingService = settingService;
    }

    @Override
    public Model begin(Model model) {
        return searchBook();
    }

    public Model searchBook() {   // 처음 검색 화면
        System.out.println("===== 도서 검색 =====");
        System.out.println("찾고자 하는 책의 제목을 입력해주세요.");
        System.out.println("(뒤로 가려면 x키를 입력하세요.)");
        System.out.println();

        // 사용자 입력 받는 부분
        String input, searchedBook;
        while(true){
            System.out.print(">>> ");
            input = sc.nextLine().trim();

            // 뒤로 가기 입력 시 사용자 메뉴 화면으로 이동
            if (input.equals("x") || input.equals("X"))
                return new Model("/user", null);

            searchedBook = validationService.BookNameValidation(input);

            if (searchedBook == null) {
                System.out.println("유효하지 않은 입력입니다.");
            }
            else break;
        }

        List<Book> searchedBookList = bookFileManager.loadBookListByName(searchedBook);
        if (searchedBookList.isEmpty()) { // 검색한 도서 제목을 가진 책이 없으면
            System.out.println("해당 도서가 등록되어 있지 않습니다.");
            return new Model("/user", null);
        } else { // 검색한 도서가 존재하면
            return printSearchedBook(searchedBook, searchedBookList);
        }
    }

    public Model printSearchedBook(String searchedBook, List<Book> searchedBookList) {   // 검색된 책 보여주는 화면
        System.out.println();
        System.out.println("==== 검색한 도서 : " + searchedBook + " ====");
        System.out.println("검색한 도서에 대해서는");
        System.out.println("'제목 / 저자 / ISBN / 대출가능여부' 순으로 출력됩니다.");
        int idx = 1;
        for (Book book : searchedBookList) {
            System.out.println(idx + ". " + book.getBookName() + " / "+ book.getAuthorName() + " / " + book.getISBN() + " / " + (book.getIsCheckout().equals("n")?"대출가능":"대출불가"));
            idx++;
        }
        System.out.println();
        System.out.println("어떤 도서를 대출하시겠습니까?");
        System.out.println("(뒤로 가려면 x키를 입력하세요.)");
        System.out.print(">>> ");
        String input;
        String userChoice;

        while (true) {
            input = sc.nextLine().trim();
            userChoice = validationService.menuInputValidation(input);

            if (userChoice.equals("X")) return searchBook();

            if (userChoice.equals("false")) {
                System.out.println("올바르지 않은 입력입니다. 다시 입력해주세요.");
                System.out.print(">>> ");
                continue;
            }

            if (Integer.parseInt(userChoice) >= 1 && Integer.parseInt(userChoice) <= searchedBookList.size()) {
                Book selectedBook = searchedBookList.get(Integer.parseInt(userChoice) - 1);
                return selectBook(searchedBook, selectedBook);
            } else {
                System.out.println("올바르지 않은 입력입니다. 다시 입력해주세요.");
                System.out.println(">>> ");
            }

        }

    }

    public Model selectBook(String searchedBook, Book selectedBook) {
        System.out.println();
        System.out.println("선택한 도서 : " + selectedBook.getBookName() + " / " + selectedBook.getAuthorName() + " / " + selectedBook.getISBN());
        System.out.println("해당 도서를 대출하시겠습니까? [yes/no]");
        System.out.println("(뒤로 가려면 x키를 입력하세요.)");
        System.out.print(">>> ");

        String input;
        String yesOrNo;
        int checkoutDuration = settingService.getCheckoutDuration(); //setting에 저장된 반납기간 값

        while (true) {
            input = sc.nextLine();
            if (input.equals("x") || input.equals("X")) return  printSearchedBook(searchedBook, bookFileManager.loadBookListByName(searchedBook));

            yesOrNo = validationService.ynInputValidation(input);

            if (yesOrNo.equals("false")) {
                System.out.println("올바르지 않은 입력입니다. 다시 입력해주세요.");
                System.out.print(">>> ");
                continue;
            }

            if (yesOrNo.equals("no")) return new Model("/user", null);

            if (yesOrNo.equals("yes")) {

                // 다른 사람이 이미 대출한 도서면 메시지 출력 후 도서 검색으로 이동
                if (selectedBook.getIsCheckout().equals("y")) {
                    System.out.println("다른 사람이 대출한 도서입니다.");
                    return searchBook();
                }

                if (blackListFileManager.isBlackList(LoginMember.getInstance(), LoginMember.getLoginTime())) {
                    // 사용자가 블랙리스트인 경우 처리
                    System.out.println("대출 중인 도서를 반납하지 않아서 블랙리스트에 추가되었습니다.");
                    System.out.println("해당 기간이 끝난 후 다시 시도해주세요.");
                    return new Model("/user", null);
                } else { // 대출 의사 yes이고 블랙리스트도 아닌 경우
                    // 대출처리
                    List<Checkout> userCheckoutList = checkoutFileManager.loadCheckoutByUser(LoginMember.getInstance());
                    if (userCheckoutList.size() >= 5) {
                        System.out.println("더이상 대출할 수 없습니다.");
                        return new Model("/user", null);
                    }
                    Checkout newCheckout = new Checkout(LoginMember.getInstance().getUserId(), selectedBook.getBookId(), LoginMember.getLoginTime(), LoginMember.getLoginTime().plusDays(checkoutDuration), null);
                    selectedBook.setIsCheckout("y");
                    bookFileManager.updateBook(selectedBook);
                    checkoutFileManager.addCheckout(newCheckout);
                    System.out.println("대출되었습니다.");
                    return new Model("/user", null);
                }
            }
        }
    }

    @Override
    public String getUri() {
        return "/user/searchandcheckout";
    }
}
