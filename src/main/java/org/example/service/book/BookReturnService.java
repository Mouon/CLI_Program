package org.example.service.book;

import org.example.domain.BlackList;
import org.example.domain.Checkout;
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

    public void recordReturnDate(Checkout checkout) {
        checkout.setReturnDate(LocalDate.now());
        checkoutFileManager.updateCheckout(checkout);
    }



    public void recordOverdate (Checkout checkout){
        LocalDate dueDate = checkout.getDueDate();  //expect
        LocalDate returnDate = checkout.getReturnDate();  //now

        if(returnDate.isBefore(dueDate) || returnDate.isEqual(dueDate)){
            checkout.setReturnDate(returnDate);
            checkoutFileManager.updateCheckout(checkout);
        } else {
            blackListFileManager.addBlackList(new BlackList(checkout.getUserId(), checkout.getReturnDate(), checkout.getReturnDate().plusDays(6)));
        }
    }
}


