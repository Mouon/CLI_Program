package org.example.service.host;

import org.example.domain.Book;
import org.example.file.BookFileManager;

import java.util.List;

public class HostCheckStateService {
    public BookFileManager bookFileManager;

    public HostCheckStateService(BookFileManager bookFileManager) {
        this.bookFileManager = bookFileManager;
    }

    public List<Book> getBookList(String bookName){
        return bookFileManager.loadBookListByName(bookName);
    }
}
