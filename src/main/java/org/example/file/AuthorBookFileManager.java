package org.example.file;

import org.example.domain.AuthorBook;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthorBookFileManager {
    public static final String FILE_PATH = "src/main/resources/author_book.txt";
    public List<AuthorBook> loadAuthorBookFileManagerList() {
        List<AuthorBook> authorBookList = new ArrayList<>();
        try {
            Scanner file = new Scanner(new File(FILE_PATH));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                AuthorBook authorBook = new AuthorBook.Builder()
                        .authorId(Long.parseLong(result[0].trim()))
                        .bookId(Long.parseLong(result[1].trim()))
                        .build();

                authorBookList.add(authorBook);
            }
            return authorBookList;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    public List<AuthorBook> loadByAuthorId(Long authorId) {
        List<AuthorBook> authorBookList = new ArrayList<>();
        try {
            Scanner file = new Scanner(new File(FILE_PATH));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                if(Long.parseLong(result[0].trim())==authorId){
                    AuthorBook authorBook = new AuthorBook.Builder()
                            .authorId(Long.parseLong(result[0].trim()))
                            .bookId(Long.parseLong(result[1].trim()))
                            .build();
                    authorBookList.add(authorBook);
                }
            }
            return authorBookList;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    public List<AuthorBook> loadByBookId(Long bookId) {
        List<AuthorBook> authorBookList = new ArrayList<>();
        try {
            Scanner file = new Scanner(new File(FILE_PATH));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                if(Long.parseLong(result[1].trim())==bookId){
                    AuthorBook authorBook = new AuthorBook.Builder()
                            .authorId(Long.parseLong(result[0].trim()))
                            .bookId(Long.parseLong(result[1].trim()))
                            .build();
                    authorBookList.add(authorBook);
                }
            }
            return authorBookList;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    public void addAuthorBook(AuthorBook authorBook) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILE_PATH), true));

            String authorBookString =  authorBook.getAuthorId() + "\t" + authorBook.getBookId();

            writer.write(authorBookString);
            writer.newLine();

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
