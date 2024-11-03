package org.example.view.user;

import org.example.domain.Book;
import org.example.domain.Checkout;
import org.example.dto.LoginMember;
import org.example.dto.Model;
import org.example.file.BookFileManager;
import org.example.file.CheckoutFileManager;
import org.example.service.book.BookReturnService;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

import java.util.List;
import java.util.Scanner;

public class UserBookReturnView implements CustomView {

    private CheckoutFileManager checkoutFileManager;
    private BookFileManager bookFileManager;
    private ValidationService validationService;
    private BookReturnService bookReturnService;

    public UserBookReturnView(CheckoutFileManager checkoutFileManager, BookFileManager bookFileManager, ValidationService validationService, BookReturnService bookReturnService) {
        this.checkoutFileManager = checkoutFileManager;
        this.bookFileManager = bookFileManager;
        this.validationService = validationService;
        this.bookReturnService = bookReturnService;
    }

    @Override
    public Model begin(Model model) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=====도서 반납=====");

        String userName = LoginMember.getInstance().getUserName();
        System.out.println("'" + userName + "' 님의 도서 대출 목록입니다. ");
        System.out.println("반납하고자 하는 책을 선택해주세요.");
        System.out.println("(뒤로 가려면 x키를 입력하세요)");

        // load
        List<Checkout> checkoutList = checkoutFileManager.loadCheckoutByUser(LoginMember.getInstance());
        if (checkoutList == null || checkoutList.isEmpty()) {
            System.out.println("대출 중인 도서가 없습니다.");
        } else {
            int i = 1;
            for (Checkout checkout : checkoutList) {
                Book book = bookFileManager.loadBookById(checkout.getBookId());
                System.out.println(i + ". " + book.getBookName());
                i++;
            }
        }

        String input="";


        //input1
        while (true) {
            System.out.print(">>>");
            input = scanner.nextLine().trim();
            if (validationService.menuInputValidation(input).equals("X")) {
                return new Model("user/", "null");
            } else if (validationService.numberInputValidation(input) != null) {
                break;
            }
            System.out.println("올바르지 않은 입력입니다.");

        }

        int index=Integer.parseInt(input);
        Book book = bookFileManager.loadBookById(checkoutList.get(index - 1).getBookId());

        //input2
        System.out.println("해당 도서를 반납하시겠습니까? [yes/no]");
        System.out.println("(뒤로 가려면 x키를 입력하세요)");
        while (true) {
            System.out.print(">>>");
            input = scanner.nextLine().trim();
            if (validationService.menuInputValidation(input).equals("X")) {
                return new Model("/user", null);
            } else if (validationService.ynInputValidation(input).equals("yes")) {
                break;
            } else if (validationService.ynInputValidation(input).equals("no")) {
                return new Model("/user", null);
            } else {
                System.out.println("올바르지 않은 입력입니다.");
            }
        }

        //update
        book.setIsCheckout("n");
        bookFileManager.updateBook(book);

        //time
        Checkout checkout = checkoutList.get(index-1);
        bookReturnService.recordReturnDate(checkout);

        //blacklist
        bookReturnService.recordOverdate(checkout);

        return new Model("/user", null);
    }

    @Override
    public String getUri() {
        return "/user/bookreturn";
    }
}



