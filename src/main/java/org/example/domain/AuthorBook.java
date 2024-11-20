package org.example.domain;

public class AuthorBook {
    private Long authorId;
    private Long bookId;

    public AuthorBook(Long authorId, Long bookId) {
        this.authorId = authorId;
        this.bookId = bookId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    /**빌더패턴*/
    public static class Builder {
        private Long authorId;
        private Long bookId;
        public Builder() {
        }
        public Builder authorId(Long authorId) {
            this.authorId = authorId;
            return this;
        }
        public Builder bookId(Long bookId) {
            this.bookId = bookId;
            return this;
        }
        public AuthorBook build() {
            return new AuthorBook(authorId, bookId);
        }
    }
}
