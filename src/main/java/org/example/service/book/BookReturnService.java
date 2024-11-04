package org.example.service.book;

import org.example.domain.BlackList;
import org.example.domain.Checkout;
import org.example.dto.LoginMember;
import org.example.file.BlackListFileManager;
import org.example.file.CheckoutFileManager;

import java.time.LocalDate;

public class BookReturnService {
    private CheckoutFileManager checkoutFileManager;
    private BlackListFileManager blackListFileManager;

    public BookReturnService(CheckoutFileManager checkoutFileManager, BlackListFileManager blackListFileManager) {
        this.checkoutFileManager = checkoutFileManager;
        this.blackListFileManager = blackListFileManager;
    }

    // 대출 기록 반납일에 기록
    public void recordReturnDate(Checkout checkout,LocalDate loginTime) {
        checkout.setReturnDate(loginTime);
        checkoutFileManager.updateCheckout(checkout);
    }

    // 대출된 도서의 연체일을 기록한다
    public void recordOverdate (Checkout checkout){
        LocalDate dueDate = checkout.getDueDate();  //expected return book date
        LocalDate returnDate = checkout.getReturnDate();  //return book date now

        //도서 반환 예정일 이전에 날짜에 기입하고, 후에 블랙리스트에 들어간다
        if(returnDate.isBefore(dueDate) || returnDate.isEqual(dueDate)){
            checkout.setReturnDate(returnDate);
            checkoutFileManager.updateCheckout(checkout);
        } else {
            blackListFileManager.addBlackList(new BlackList(checkout.getUserId(), checkout.getReturnDate(), checkout.getReturnDate().plusDays(7)));
        }
    }
}
