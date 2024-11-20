package org.example.domain;

import java.time.LocalDate;

public class Author {
    private Long authorId;
    private String authorName;
    private LocalDate birthDate;

    public Author(Long authorId, String authorName, LocalDate birthDate) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.birthDate = birthDate;
    }

    public Author(String authorName, LocalDate birthDate) {
        this.authorName = authorName;
        this.birthDate = birthDate;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**빌더패턴*/
    public static class Builder {
        private Long authorId;
        private String authorName;
        private LocalDate birthDate;
        public Builder() {
        }
        public Builder authorId(Long authorId) {
            this.authorId = authorId;
            return this;
        }
        public Builder authorName(String authorName) {
            this.authorName = authorName;
            return this;
        }
        public Builder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }
        public Author build() {
            return new Author(authorId, authorName, birthDate);
        }
    }
}
