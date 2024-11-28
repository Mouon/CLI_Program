package org.example.service;

import org.example.domain.*;
import org.example.file.*;

import java.util.List;

/*
책 id를 넣으면 상세 정보를 출력해주는 함수
 */
public class ShowBookDetailService {

    public BookFileManager bookFileManager;
    public AuthorBookFileManager authorBookFileManager;
    public AuthorFileManger authorFileManger;
    public CheckoutFileManager checkoutFileManager;

    public ShowBookDetailService(BookFileManager bookFileManager, AuthorBookFileManager authorBookFileManager, AuthorFileManger authorFileManger, CheckoutFileManager checkoutFileManager){
        this.bookFileManager = bookFileManager;
        this.authorFileManger = authorFileManger;
        this.authorBookFileManager = authorBookFileManager;
        this.checkoutFileManager = checkoutFileManager;
    }

    public void showBookDetail(long bookid){
        Book currentBook = bookFileManager.loadBookById(bookid);

        System.out.println("도서명 : " + currentBook.getBookName());
        System.out.println("출판사 : " + currentBook.getPublishingHouse());
        System.out.println("ISBN : " + currentBook.getISBN());
        System.out.println("대출 중 여부(y/n) : " + currentBook.getIsCheckout());
        System.out.println("등록일 : " + currentBook.getEnterDate());
        if(currentBook.getDeleteDate() == null){
            System.out.println("등록 중인 도서입니다.");
        }
        else{
            System.out.println("삭제일 : " + currentBook.getDeleteDate());
        }

        System.out.println("\n====저자 목록====");
        System.out.println("저자 번호 / 저자 명 / 저자 생년월일");
        List<AuthorBook> authorList = authorBookFileManager.loadByBookId(currentBook.getBookId());
        for (AuthorBook authorBook : authorList) {
            long authorID = authorBook.getAuthorId();
            Author currentAuthor;
            try{
                currentAuthor = authorFileManger.loadAuthorById(authorID);
            }
            catch (Exception e){
                System.out.println("저자 관련 파일에 오류가 있습니다.\n");
                continue;
            }
            System.out.print(currentAuthor.getAuthorId() + " / ");
            System.out.print(currentAuthor.getAuthorName() + " / ");
            System.out.print(currentAuthor.getBirthDate() + "\n");
        }
        System.out.println();

        System.out.println("====대출 기록====");
        System.out.println("사용자 / 대출일 / 반납일");
        List<Checkout> checkoutList = checkoutFileManager.loadCheckoutList();
        for(Checkout checkout : checkoutList){
            if(checkout.getBookId() == bookid){
                System.out.print(checkout.getUserId() + " / ");
                System.out.print(checkout.getCheckoutDate() + " / ");
                if(checkout.getReturnDate() == null){
                    System.out.println("-");
                }
                else{
                    System.out.println(checkout.getReturnDate());
                }
            }
        }
    }
}
