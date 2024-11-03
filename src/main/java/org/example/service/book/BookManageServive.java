package org.example.service.book;

import org.example.domain.Book;
import org.example.file.BookFileManager;

public class BookManageServive {

    public BookFileManager bookFileManager;

    public BookManageServive(BookFileManager bookFileManager) {this.bookFileManager = bookFileManager;}

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
