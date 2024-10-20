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

/**
 * @class CheckoutFileManager
 * @description 도서 대출(체크아웃) 정보 관련 파일 작업을 관리하는 클래스
 *
 * 이 클래스는 도서 대출 데이터의 로드, 추가, 업데이트 등의 기능을 제공합니다.
 * 대출 정보는 파일 시스템에 저장되며, 이 클래스를 통해 관리됩니다.
 */
public class CheckoutFileManager {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public CheckoutFileManager() {
    }

    /**
     * 모든 체크아웃 리스트를 로드한다.
     *
     * @return 모든 체크아웃 항목을 포함하는 List<Checkout>
     */
    public List<Checkout> loadCheckoutList() {
        List<Checkout> checkoutList = new ArrayList<>();
        try {
            Scanner file = new Scanner(new File("src/main/resources/checkout.txt"));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                checkoutList.add(new Checkout(result[0].trim(), Long.parseLong(result[1].trim()), LocalDate.parse(result[2].trim(),DATE_FORMATTER),
                        LocalDate.parse(result[3].trim(), DATE_FORMATTER),result[4].trim().equals("null") ? null : LocalDate.parse(result[4].trim(), DATE_FORMATTER)));
            }
            return checkoutList;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     * 특정 책의 체크아웃 정보를 찾는 메서드
     *
     * @param book 검색할 Book 객체
     * @return 해당 책의 Checkout 객체, 없으면 null
     *
     * 사용 예시:
     * BookFileManager bookFileManager = new BookFileManager();
     * CheckoutFileManager checkoutFileManager = new CheckoutFileManager();
     * UserFileManager userFileManager = new UserFileManager();
     *
     * Book book = bookFileManager.loadBookByName("개미");
     * System.out.println(userFileManager.loadUserById(checkoutFileManager.loadCheckoutByBook(book).getUserId()).getUserName());
     */
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
     * 특정 사용자의 체크아웃 정보를 찾는 메서드
     *
     * @param user 검색할 User 객체
     * @return 해당 사용자의 체크아웃 목록을 포함하는 List<Checkout>
     */
    public List<Checkout> loadCheckoutByUser(User user) {
        List<Checkout> checkoutList = new ArrayList<>();
        try {
            Scanner file = new Scanner(new File("src/main/resources/checkout.txt"));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                if(result[0].trim().equals(user.getUserId())){
                    checkoutList.add(new Checkout(result[0].trim(), Long.parseLong(result[1].trim()), LocalDate.parse(result[2].trim(), DATE_FORMATTER),
                            LocalDate.parse(result[3].trim(), DATE_FORMATTER),result[4].trim().equals("null") ? null : LocalDate.parse(result[4].trim(), DATE_FORMATTER)));
                }
            }
            return checkoutList;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     * 새로운 체크아웃 정보를 추가하는 메서드
     *
     * @param checkout 추가할 Checkout 객체
     */
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

    /**
     * 체크아웃 정보를 업데이트하는 메서드
     *
     * @param updatedCheckout 업데이트할 Checkout 객체
     *
     * 사용 예시:
     * UserFileManager userFileManager = new UserFileManager();
     * CheckoutFileManager checkoutFileManager = new CheckoutFileManager();
     * BookFileManager bookFileManager = new BookFileManager();
     * User user = userFileManager.loadUserById("hello123");
     * Checkout checkout = checkoutFileManager.loadCheckoutByBook(bookFileManager.loadBookById(2L));
     * checkout.setReturnDate(LocalDate.now());
     * checkoutFileManager.updateCheckout(checkout);
     */
    public void updateCheckout(Checkout updatedCheckout) {
        List<Checkout> checkoutList = loadCheckoutList();
        boolean isUpdated = false;

        for (int i = 0; i < checkoutList.size(); i++) {
            if (checkoutList.get(i).getUserId().equals(updatedCheckout.getUserId())&&
                    checkoutList.get(i).getBookId()==updatedCheckout.getBookId()) {
                checkoutList.set(i, updatedCheckout);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/checkout.txt")));

                for (Checkout checkout : checkoutList) {
                    String checkoutString = checkout.getUserId() + "\t" + checkout.getBookId() + "\t"
                                    + checkout.getCheckoutDate().format(DATE_FORMATTER) + "\t"
                                    + checkout.getDueDate().format(DATE_FORMATTER) + "\t"
                                    + (checkout.getReturnDate() != null ? checkout.getReturnDate().format(DATE_FORMATTER) : "null");
                    writer.write(checkoutString);
                    writer.newLine();
                }

                writer.flush();
                writer.close();
            } catch (IOException e) {
                System.out.println("체크아웃 정보 업데이트 중 오류가 발생했습니다.");
                e.printStackTrace();
            }
        } else {
            System.out.println("해당 체크아웃 찾을 수 없습니다.");
        }
    }


}
