package org.example.service.book;

import org.example.domain.Book;
import org.example.file.BookFileManager;

import java.time.LocalDate;

public class BookManageService {

    public BookFileManager bookFileManager;

    public BookManageService(BookFileManager bookFileManager) {this.bookFileManager = bookFileManager;}

    public void addBook(String bookName, String publishingHouse, String publishingYear, int amount, String ISBN, LocalDate enterDate){
        while(amount>0) {
            bookFileManager.addBook(new Book(bookName, publishingHouse, publishingYear, "n", ISBN , enterDate));
            amount--;
        }
    }
    public void removeBook(Book book){
            bookFileManager.removeBook(book);
    }
}
