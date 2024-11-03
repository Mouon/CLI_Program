package org.example.service.host;

import org.example.domain.Book;
import org.example.file.BookFileManager;

import java.util.List;

public class HostShowListService {
    public BookFileManager bookFileManager;
    public HostShowListService(BookFileManager bookFileManager) {
        this.bookFileManager = bookFileManager;
    }

    public List<Book> getBookList(){
        return bookFileManager.loadBookList();
    }
}
