package org.example.domain;

public class Book {
    private Long bookId;
    private String bookName;
    private String authorName;
    private String publishingHouse;
    private String publishingYear;
    private String isCheckout;

    public Book(Long bookId, String bookName, String authorName,
                String publishingHouse, String publishingYear, String isCheckout) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.publishingHouse = publishingHouse;
        this.publishingYear = publishingYear;
        this.isCheckout = isCheckout;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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
}
