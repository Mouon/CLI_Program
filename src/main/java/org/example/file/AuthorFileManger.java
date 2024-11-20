package org.example.file;

import org.example.domain.Author;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @class AuthorFileManger
 * @description 작가 관련 파일 작업을 관리하는 클래스
 *
 * 이 클래스는 작가 데이터의 로드, 추가, 삭제, 업데이트 등의 기능을 제공합니다.
 * 작가 정보는 파일 시스템에 저장되며, 이 클래스를 통해 관리됩니다.
 */
public class AuthorFileManger {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final String FILE_PATH = "src/main/resources/author.txt";

    public List<Author> loadAuthorList() {
        List<Author> authorList = new ArrayList<>();
        try {
            Scanner file = new Scanner(new File(FILE_PATH));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                Author author = new Author.Builder()
                        .authorId(Long.parseLong(result[0].trim()))
                        .authorName(result[1].trim())
                        .birthDate(LocalDate.parse(result[2].trim(), DATE_FORMATTER))
                        .ISNI(result[3].trim())
                        .build();

                authorList.add(author);
            }
            return authorList;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }
    /**
     * @param authorId 검색할 작가의 ID
     * @return 해당 ID의 도서 객체, 없으면 null
     * */
    public Author loadAuthorById(Long authorId) {
        try {
            Scanner file = new Scanner(new File(FILE_PATH));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                if(Long.parseLong(result[0].trim())==authorId){
                    Author author = new Author.Builder()
                            .authorId(Long.parseLong(result[0].trim()))
                            .authorName(result[1].trim())
                            .birthDate(LocalDate.parse(result[2].trim(), DATE_FORMATTER))
                            .ISNI(result[3].trim())
                            .build();
                    return author;
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     * @ description 작가를 추가하는 함수
     * */
    public void addAuthor(Author author) {
        long authorId = getNextAuthorId();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILE_PATH), true));

            String authorString =  authorId + "\t" + author.getAuthorName()  + "\t"
                    + author.getBirthDate()+"\t" + author.getUID();

            writer.write(authorString);
            writer.newLine();

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long getNextAuthorId() {
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
