package org.example.service.book;

import org.example.domain.Author;
import org.example.domain.AuthorBook;
import org.example.domain.Book;
import org.example.file.AuthorBookFileManager;
import org.example.file.BookFileManager;

import java.time.LocalDate;
import java.util.List;

public class BookManageService {

    public BookFileManager bookFileManager;
    public AuthorBookFileManager authorBookFileManager;

    public BookManageService(BookFileManager bookFileManager,AuthorBookFileManager authorBookFileManager) {this.bookFileManager = bookFileManager;this.authorBookFileManager = authorBookFileManager;}

    public void addBook(String bookName, String publishingHouse, String publishingYear, int amount, String ISBN, LocalDate enterDate, List<Author> authors) {
        while(amount>0) {
            long bookId = bookFileManager.addBook(new Book(bookName, publishingHouse, publishingYear, "n", ISBN , enterDate));
            for (Author author : authors) {
                newAuthorBook(bookId, author);
            }

            amount--;
        }
    }
    public void removeBook(Book book){
            bookFileManager.removeBook(book);
    }
    public void newAuthorBook(long bookId, Author author){
        authorBookFileManager.addAuthorBook(new AuthorBook(author.getAuthorId(),bookId));
    }
}
