package org.example.file;

import org.example.domain.Book;
import org.example.domain.Checkout;
import org.example.domain.User;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CheckoutFileManager {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public CheckoutFileManager() {
    }

    /**
     * 모든 체크아웃 리스트 업로드
     * */
    public List<Checkout> loadCheckoutList() {
        List<Checkout> checkoutList = new ArrayList<>();
        try {
            Scanner file = new Scanner(new File("src/main/resources/checkout.txt"));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                checkoutList.add(new Checkout(result[0].trim(), Long.parseLong(result[1].trim()), LocalDate.parse(result[2].trim(),DATE_FORMATTER),
                        LocalDate.parse(result[3].trim(), DATE_FORMATTER),LocalDate.parse(result[4].trim(),DATE_FORMATTER)));
            }
            return checkoutList;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     * 책으로로 찾는 메서드
     * @ 예시 :
     *         BookFileManager bookFileManager = new BookFileManager();
     *         CheckoutFileManager checkoutFileManager = new CheckoutFileManager();
     *         UserFileManager userFileManager = new UserFileManager();
     *
     *         Book book = bookFileManager.loadBookByName("개미");
     *         System.out.println(userFileManager.loadUserById(checkoutFileManager.loadCheckoutByBook(book).getUserId()).getUserName());
     *
     * */
    public Checkout loadCheckoutByBook(Book book) {
        try {
            Scanner file = new Scanner(new File("src/main/resources/checkout.txt"));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                if(Long.parseLong(result[1].trim())== book.getBookId()){
                    return new Checkout(result[0].trim(), Long.parseLong(result[1].trim()), LocalDate.parse(result[2].trim(), DATE_FORMATTER),
                            LocalDate.parse(result[3].trim(), DATE_FORMATTER),result[4].trim().equals("null") ? null : LocalDate.parse(result[4].trim(), DATE_FORMATTER));
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     * 체크아웃 추가
     * */
    public void addCheckout(Checkout checkout) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/checkout.txt"), true));

            String checkoutString = checkout.getUserId() + "\t" + checkout.getBookId() + "\t"
                    + checkout.getCheckoutDate().format(DATE_FORMATTER) + "\t"
                    + checkout.getDueDate().format(DATE_FORMATTER) + "\t"
                    + (checkout.getReturnDate() != null ? checkout.getReturnDate().format(DATE_FORMATTER) : "null");

            writer.write(checkoutString);
            writer.newLine();

            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("체크아웃 추가 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
}
