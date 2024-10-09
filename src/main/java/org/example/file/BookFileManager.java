package org.example.file;

import org.example.domain.Book;
import org.example.domain.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookFileManager {
    public BookFileManager() {
    }

    public List<Book> loadBookList() {
        List<Book> bookList = new ArrayList<>();
        try {
            Scanner file = new Scanner(new File("src/main/resources/books.txt"));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                bookList.add(new Book(Long.parseLong(result[0].trim()), result[1].trim(), result[2].trim(),
                        result[3].trim(),result[4].trim(),result[5].trim()));
            }
            return bookList;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     * 이름으로 책 찾는 메서드
     *
     * */
    public List<Book> loadBookListByName(String bookName) {
        List<Book> bookList = new ArrayList<>();
        try {
            Scanner file = new Scanner(new File("src/main/resources/books.txt"));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                if(result[1].trim().equals(bookName)){
                    bookList.add(new Book(Long.parseLong(result[0].trim()), result[1].trim(), result[2].trim(),
                            result[3].trim(),result[4].trim(),result[5].trim()));
                }
            }
            return bookList;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     * 아이디로 책 찾는 메서드
     * */
    public Book loadBookById(Long bookId) {
        try {
            Scanner file = new Scanner(new File("src/main/resources/books.txt"));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                if(Long.parseLong(result[0].trim())==bookId){
                    return new Book(Long.parseLong(result[0].trim()), result[1].trim(), result[2].trim(),
                            result[3].trim(),result[4].trim(),result[5].trim());
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     * 새로운 도서를 파일에 추가하는 메서드 (addBook)
     */
    public void addBook(Book book) {
        long bookId = getNextBookId();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/books.txt"), true));

            String bookString =  bookId + "\t" + book.getBookName() + "\t" + book.getAuthorName() + "\t"
                    + book.getPublishingHouse()+"\t" + book.getPublishingYear()+"\t" + book.getIsCheckout();

            writer.write(bookString);
            writer.newLine();

            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("도서 추가 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    /**
     *
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
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/books.txt")));

                for (Book book : bookList) {
                    String bookString =  book.getBookId() + "\t" + book.getBookName() + "\t" + book.getAuthorName() + "\t"
                            + book.getPublishingHouse()+"\t" + book.getPublishingYear()+"\t" + book.getIsCheckout();
                    writer.write(bookString);
                    writer.newLine();
                }

                writer.flush();
                writer.close();
            } catch (IOException e) {
                System.out.println("책 정보 업데이트 중 오류가 발생했습니다.");
                e.printStackTrace();
            }
        } else {
            System.out.println("해당 책 찾을 수 없습니다.");
        }
    }

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
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/books.txt")));

                for (Book book : bookList) {
                    String bookString =  book.getBookId() + "\t" + book.getBookName() + "\t" + book.getAuthorName() + "\t"
                            + book.getPublishingHouse()+"\t" + book.getPublishingYear()+"\t" + book.getIsCheckout();
                    writer.write(bookString);
                    writer.newLine();
                }

                writer.flush();
                writer.close();
            } catch (IOException e) {
                System.out.println("책 정보 업데이트 중 오류가 발생했습니다.");
                e.printStackTrace();
            }
        } else {
            System.out.println("해당 책 찾을 수 없습니다.");
        }
    }

    /**
     * 현재 파일에서 가장 높은 책 ID를 찾아 다음 ID를 반환하는 메서드입니다.
     */
    private long getNextBookId() {
        long maxId = -1; // 기본적으로 -1로 초기화 (유효한 ID는 0 이상)

        try {
            Scanner file = new Scanner(new File("src/main/resources/books.txt"));
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
