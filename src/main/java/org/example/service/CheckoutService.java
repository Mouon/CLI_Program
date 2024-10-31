package org.example.service;

import org.example.domain.Book;
import org.example.domain.Checkout;
import org.example.domain.User;
import org.example.dto.LoginMember;
import org.example.file.BookFileManager;
import org.example.file.CheckoutFileManager;
import org.example.file.UserFileManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckoutService {
    public UserFileManager userFileManager;
    public CheckoutFileManager checkoutFileManager;
    public BookFileManager bookFileManager;

    public CheckoutService(UserFileManager userFileManager, CheckoutFileManager checkoutFileManager, BookFileManager bookFileManager) {
        this.userFileManager = userFileManager;
        this.checkoutFileManager = checkoutFileManager;
        this.bookFileManager = bookFileManager;
    }

    /**
     * 사용자의 도서 대출 내역 불러오기
     * checkoutFileManager에서 불러온 checkout들 중에 반납일과 비교하여
     * 출력할 String들을 담은 List를 return한다.
     */
    public List<String> getCheckoutHistory(User user){
        LocalDate loginDate = LoginMember.getLoginTime();
        List<Checkout> totalCheckouts = checkoutFileManager.loadCheckoutByUser(user);
        List<String> checkoutsToPrint = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        for(Checkout checkout : totalCheckouts){
            String checkoutString = "";
            Book checkedoutBook = bookFileManager.loadBookById(checkout.getBookId());

            //로그인 날짜보다 대출일이 이전이거나 같은 경우들만 list에 추가
            if(checkout.getCheckoutDate().isBefore(loginDate) || checkout.getCheckoutDate().isEqual(loginDate)){
                //출력할 string의 도서 정보, 대출일, 반납예정일 부분
                checkoutString += checkedoutBook.getBookName()+" / "
                        +checkedoutBook.getAuthorName()+" / "
                        +checkout.getCheckoutDate()+" / "
                        +checkout.getDueDate()+" / ";

                //아직 대출중인 도서의 경우 (반납일이 null이거나 로그인 날짜보다 이후)
                if(checkout.getReturnDate()==null || checkout.getReturnDate().isAfter(loginDate) ){
                    checkoutString += "대출중";
                    checkoutsToPrint.add(checkoutString);
                }else{//반납 완료된 도서의 경우
                    checkoutString += checkout.getReturnDate().format(formatter)+" / ";
                    if(checkout.getReturnDate().isAfter(checkout.getDueDate())){//연체인 경우
                        checkoutString += "연체반납";
                        checkoutsToPrint.add(checkoutString);
                    }else{//연체가 아닌경우
                        checkoutString += "정상반납";
                        checkoutsToPrint.add(checkoutString);
                    }
                }
            }
        }
        return checkoutsToPrint;
    }
}
