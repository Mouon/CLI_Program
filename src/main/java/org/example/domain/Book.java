package org.example.domain;

import org.example.file.AuthorBookFileManager;
import org.example.file.AuthorFileManger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Book {
    private Long bookId;
    private String bookName;
    private String publishingHouse;
    private String publishingYear;
    private String isCheckout;
    private String ISBN;
    private LocalDate enterDate;
    private LocalDate deleteDate;
    private AuthorBookFileManager authorBookFileManager;
    private AuthorFileManger authorFileManager;
    public Book(AuthorBookFileManager authorBookFileManager, AuthorFileManger authorFileManager) {
        this.authorBookFileManager = authorBookFileManager;
        this.authorFileManager = authorFileManager;
    }

    public Book(String bookName, String publishingHouse, String publishingYear,
                String isCheckout, String ISBN, LocalDate enterDate, LocalDate deleteDate) {
        this.bookName = bookName;
        this.publishingHouse = publishingHouse;
        this.publishingYear = publishingYear;
        this.isCheckout = isCheckout;
        this.ISBN = ISBN;
        this.enterDate = enterDate;
        this.deleteDate = deleteDate;
    }

    public Book(String bookName, String publishingHouse, String publishingYear,
                String isCheckout, String ISBN, LocalDate enterDate) {
        this.bookName = bookName;
        this.publishingHouse = publishingHouse;
        this.publishingYear = publishingYear;
        this.isCheckout = isCheckout;
        this.ISBN = ISBN;
        this.enterDate = enterDate;
    }

    public Book(Long bookId, String bookName, String publishingHouse, String publishingYear, String isCheckout, String ISBN,
                LocalDate enterDate, LocalDate deleteDate) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.publishingHouse = publishingHouse;
        this.publishingYear = publishingYear;
        this.isCheckout = isCheckout;
        this.ISBN = ISBN;
        this.enterDate = enterDate;
        this.deleteDate = deleteDate;
    }

    public LocalDate getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(LocalDate enterDate) {
        this.enterDate = enterDate;
    }

    public LocalDate getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(LocalDate deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public String getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(String publishingYear) {
        this.publishingYear = publishingYear;
    }

    public String getIsCheckout() {
        return isCheckout;
    }

    public void setIsCheckout(String isCheckout) {
        this.isCheckout = isCheckout;
    }
    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }


    public String getAuthorName() {
        if (this.bookId == null || authorBookFileManager == null || authorFileManager == null) {
            return "";
        }
        List<AuthorBook> authorBooks = authorBookFileManager.loadByBookId(this.bookId);
        List<Long> authorIds = authorBooks.stream()
                .map(AuthorBook::getAuthorId)
                .collect(Collectors.toList());

        List<String> authorNames = new ArrayList<>();
        for (Long authorId : authorIds) {
            Author author = authorFileManager.loadAuthorById(authorId);
            if (author != null) {
                authorNames.add(author.getAuthorName());
            }
        }
        return String.join(",", authorNames);
    }

    /**빌더패턴*/
    public static class Builder {
        private Long bookId;
        private String bookName;
        private String publishingHouse;
        private String publishingYear;
        private String isCheckout;
        private String ISBN;
        private LocalDate enterDate;
        private LocalDate deleteDate;
        private AuthorBookFileManager authorBookFileManager;
        private AuthorFileManger authorFileManager;
        public Builder() {
        }
        public Builder bookId(Long bookId) {
            this.bookId = bookId;
            return this;
        }
        public Builder bookName(String bookName) {
            this.bookName = bookName;
            return this;
        }
        public Builder publishingHouse(String publishingHouse) {
            this.publishingHouse = publishingHouse;
            return this;
        }
        public Builder publishingYear(String publishingYear) {
            this.publishingYear = publishingYear;
            return this;
        }
        public Builder isCheckout(String isCheckout) {
            this.isCheckout = isCheckout;
            return this;
        }
        public Builder ISBN(String ISBN) {
            this.ISBN = ISBN;
            return this;
        }
        public Builder enterDate(LocalDate enterDate) {
            this.enterDate = enterDate;
            return this;
        }
        public Builder deleteDate(LocalDate deleteDate) {
            this.deleteDate = deleteDate;
            return this;
        }
        public Builder authorBookFileManager(AuthorBookFileManager authorBookFileManager) {
            this.authorBookFileManager = authorBookFileManager;
            return this;
        }
        public Builder authorFileManager(AuthorFileManger authorFileManager) {
            this.authorFileManager = authorFileManager;
            return this;
        }
        public Book build() {
            Book book = new Book(bookId, bookName, publishingHouse, publishingYear, isCheckout, ISBN, enterDate, deleteDate);
            book.authorBookFileManager = this.authorBookFileManager;
            book.authorFileManager = this.authorFileManager;
            return book;        }
    }

}
