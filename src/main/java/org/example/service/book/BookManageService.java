package org.example.service.book;

import org.example.domain.Book;
import org.example.file.BookFileManager;

public class BookManageService {

    public BookFileManager bookFileManager;

    public BookManageService(BookFileManager bookFileManager) {this.bookFileManager = bookFileManager;}

    public void addBook(String bookName,String authorName,String publishingHouse,String publishingYear,int amount,String ISBN){
        while(amount>0) {
            bookFileManager.addBook(new Book(bookName, authorName, publishingHouse, publishingYear, "n", ISBN));
            amount--;
        }
    }
    public void removeBook(Book book){
            bookFileManager.removeBook(book);
    }
}
