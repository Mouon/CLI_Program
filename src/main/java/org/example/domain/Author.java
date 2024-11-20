package org.example.domain;

import java.time.LocalDate;

public class Author {
    private Long authorId;
    private String authorName;
    private LocalDate birthDate;
    private String ISNI;

    public Author(Long authorId, String authorName, LocalDate birthDate, String ISNI) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.birthDate = birthDate;
        this.ISNI = ISNI;
    }

    public Author(String authorName, LocalDate birthDate, String ISNI) {
        this.authorName = authorName;
        this.birthDate = birthDate;
        this.ISNI = ISNI;
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

    public String getISNI() {
        return ISNI;
    }

    public void setISNI(String ISNI) {
        this.ISNI = ISNI;
    }
    /**빌더패턴*/
    public static class Builder {
        private Long authorId;
        private String authorName;
        private LocalDate birthDate;
        private String ISNI;
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
        public Builder ISNI(String ISNI) {
            this.ISNI = ISNI;
            return this;
        }
        public Author build() {
            return new Author(authorId, authorName, birthDate, ISNI);
        }
    }
}
