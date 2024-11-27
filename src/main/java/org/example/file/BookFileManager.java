package org.example.file;

import org.example.domain.Book;
import org.example.domain.User;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @class BookFileManager
 * @description 도서 정보 관련 파일 작업을 관리하는 클래스
 *
 * 이 클래스는 도서 데이터의 로드, 추가, 삭제, 업데이트 등의 기능을 제공합니다.
 * 도서 정보는 파일 시스템에 저장되며, 이 클래스를 통해 관리됩니다.
 */
public class BookFileManager {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final String FILE_PATH = "src/main/resources/books.txt";

    public BookFileManager() {
    }

    private LocalDate parseDeleteDate(String dateStr) {
        if (dateStr == null || dateStr.trim().equals("null")) {
            return null; // 삭제일이 null인 경우 null 반환
        }
        return LocalDate.parse(dateStr.trim(), DATE_FORMATTER);
    }

    private String formatDeleteDate(LocalDate date) {
        return date == null ? "null" : date.format(DATE_FORMATTER);
    }

    private String formatIsDelete(boolean isDelete) {
        return isDelete ? "true" : "false";
    }

    /**
     * 전체 책 리스트를 로드한다.
     *
     * @return 모든 도서 항목을 포함하는 List<Book>
     */
    public List<Book> loadBookList() {
        List<Book> bookList = new ArrayList<>();
        try {
            Scanner file = new Scanner(new File(FILE_PATH));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                Book book = new Book.Builder()
                        .bookId(Long.parseLong(result[0].trim()))
                        .bookName(result[1].trim())
                        .publishingHouse(result[2].trim())
                        .publishingYear(result[3].trim())
                        .isCheckout(result[4].trim())
                        .ISBN(result[5].trim())
                        .enterDate(LocalDate.parse(result[6].trim(), DATE_FORMATTER))
                        .deleteDate(parseDeleteDate(result[7]))
                        .isDelete(result[8].trim().equals("true") ? true : false)
                        .authorBookFileManager(new AuthorBookFileManager())
                        .authorFileManager(new AuthorFileManger())
                        .build();

                bookList.add(book);
            }
            return bookList;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     * 이름으로 책을 찾는 메서드
     *
     * @param bookName 검색할 도서의 이름
     * @return 해당 이름의 도서 목록을 포함하는 List<Book>
     */
    public List<Book> loadBookListByName(String bookName) {
        List<Book> bookList = new ArrayList<>();
        try {
            Scanner file = new Scanner(new File(FILE_PATH));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                if(result[1].trim().equals(bookName)){
                    Book book = new Book.Builder()
                            .bookId(Long.parseLong(result[0].trim()))
                            .bookName(result[1].trim())
                            .publishingHouse(result[2].trim())
                            .publishingYear(result[3].trim())
                            .isCheckout(result[4].trim())
                            .ISBN(result[5].trim())
                            .enterDate(LocalDate.parse(result[6].trim(), DATE_FORMATTER))
                            .deleteDate(parseDeleteDate(result[7]))
                            .authorBookFileManager(new AuthorBookFileManager())
                            .authorFileManager(new AuthorFileManger())
                            .isDelete(result[8].trim().equals("true") ? true : false)
                            .build();
                    bookList.add(book);
                }
            }
            return bookList;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     * 아이디로 책을 찾는 메서드
     *
     * @param bookId 검색할 도서의 ID
     * @return 해당 ID의 도서 객체, 없으면 null
     */
    public Book loadBookById(Long bookId) {
        try {
            Scanner file = new Scanner(new File(FILE_PATH));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                if(Long.parseLong(result[0].trim())==bookId){
                    Book book = new Book.Builder()
                            .bookId(Long.parseLong(result[0].trim()))
                            .bookName(result[1].trim())
                            .publishingHouse(result[2].trim())
                            .publishingYear(result[3].trim())
                            .isCheckout(result[4].trim())
                            .ISBN(result[5].trim())
                            .enterDate(LocalDate.parse(result[6].trim(), DATE_FORMATTER))
                            .deleteDate(parseDeleteDate(result[7]))
                            .isDelete(result[8].trim().equals("true") ? true : false)
                            .build();
                    return book;
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     * 새로운 도서를 파일에 추가하는 메서드
     *
     * @param book 추가할 Book 객체
     *
     * 등록할땐 "deleteDate"는 문자열 "null"로 저장
     */
    public long addBook(Book book) {
        long bookId = getNextBookId();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILE_PATH), true));

            String bookString =  bookId + "\t" + book.getBookName()  + "\t"
                    + book.getPublishingHouse()+"\t" + book.getPublishingYear()+"\t" + book.getIsCheckout() +
                    "\t" + book.getISBN() + "\t" + book.getEnterDate().format(DATE_FORMATTER)+ "\t" + "null"+"\t" + "false";

            writer.write(bookString);
            writer.newLine();

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookId;
    }

    /**
     * 책을 제거하는 메서드
     *
     * @param removedBook 제거할 Book 객체
     */
    public void removeBook(Book removedBook){
        List<Book> bookList = loadBookList();
        boolean isUpdated = false;

        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getBookId().equals(removedBook.getBookId())) {
                bookList.remove(i);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILE_PATH)));

                for (Book book : bookList) {
                    String bookString =  book.getBookId() + "\t" + book.getBookName()  + "\t"
                            + book.getPublishingHouse()+"\t" + book.getPublishingYear()+"\t" + book.getIsCheckout() +
                            "\t" + book.getISBN() + "\t" + book.getEnterDate()+ "\t" + formatDeleteDate(book.getDeleteDate()) +
                            "\t" + formatIsDelete(book.isDelete());
                    writer.write(bookString);
                    writer.newLine();
                }

                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("해당 책 찾을 수 없습니다.");
        }
    }

    /**
     * 책 정보를 업데이트하는 메서드
     *
     * @param updatedBook 업데이트할 Book 객체
     */
    public void updateBook(Book updatedBook) {
        List<Book> bookList = loadBookList();
        boolean isUpdated = false;

        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getBookId().equals(updatedBook.getBookId())) {
                bookList.set(i, updatedBook);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILE_PATH)));

                for (Book book : bookList) {
                    String bookString =  book.getBookId() + "\t" + book.getBookName()  + "\t"
                            + book.getPublishingHouse()+"\t" + book.getPublishingYear()+"\t" + book.getIsCheckout() +
                            "\t" + book.getISBN() + "\t" + book.getEnterDate()+ "\t" + formatDeleteDate(book.getDeleteDate())
                            + "\t" + formatIsDelete(book.isDelete());
                    writer.write(bookString);
                    writer.newLine();
                }

                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        }
    }

    /**
     * 현재 파일에서 가장 높은 책 ID를 찾아 다음 ID를 반환하는 메서드
     *
     * @return 다음에 사용할 수 있는 도서 ID
     */
    private long getNextBookId() {
        long maxId = -1; // 기본적으로 -1로 초기화 (유효한 ID는 0 이상)

        try {
            Scanner file = new Scanner(new File(FILE_PATH));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                long currentId = Long.parseLong(result[0].trim());
                if (currentId > maxId) {
                    maxId = currentId;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }

        return maxId + 1; /** 다음 ID를 반환합니다 (가장 높은 ID + 1) */
    }

}
