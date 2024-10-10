package org.example.file;

import org.example.domain.BlackList;
import org.example.domain.User;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackListFileManager {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public BlackListFileManager() {
    }

    /**
     * 전체 블랙리스트를 로드한다.
     * */
    public List<BlackList> loadAllBlackList() {
        List<BlackList> blackLists = new ArrayList<>();
        try {
            Scanner file = new Scanner(new File("src/main/resources/blacklist.txt"));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                blackLists.add(new BlackList(result[0].trim(), LocalDate.parse(result[1].trim(),DATE_FORMATTER)
                        ,(result[2].trim().equals("null") ? null : LocalDate.parse(result[2].trim(),DATE_FORMATTER))));
            }
            return blackLists;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /** @ 사용법 :
     * blackListFileManager.loadBlackListByUserId("user1234")
     * */
    public BlackList loadBlackListByUserId(String userId) {
        try {
            Scanner file = new Scanner(new File("src/main/resources/blacklist.txt"));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                if(result[0].trim().equals(userId)){
                    return new BlackList(result[0].trim(), LocalDate.parse(result[1].trim(),DATE_FORMATTER)
                            ,(result[2].trim().equals("null") ? null : LocalDate.parse(result[2].trim(),DATE_FORMATTER)));
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     *   해당유저가 블랙리스트인지 boolean으로 반환
     *
     * @ 사용법 :
     *         UserFileManager userFileManager = new UserFileManager();
     *         BlackListFileManager blackListFileManager = new BlackListFileManager();
     *         User user = userFileManager.loadUserById("hello123");
     *         System.out.println(blackListFileManager.isBlackList(user, LocalDate.now()));
     * */
    public boolean isBlackList(User user, LocalDate localDate) {
        try {
            Scanner file = new Scanner(new File("src/main/resources/blacklist.txt"));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                if(result[0].trim().equals(user.getUserId())&&result[2].trim().equals("null")){
                    LocalDate startDate = LocalDate.parse(result[1].trim(), DATE_FORMATTER);
                    LocalDate endDate = LocalDate.parse(result[2].trim(), DATE_FORMATTER);
                    if ((localDate.isEqual(startDate) || localDate.isAfter(startDate))
                            && (localDate.isEqual(endDate) || localDate.isBefore(endDate))) {
                        return true;
                    } else {
                        System.out.println("로그인 날짜가 블랙리스트 기간에 포함되지 않습니다.");
                        return false;
                    }
                }
            }
            return false;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    public void addBlackList(BlackList blackList) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/blacklist.txt"), true));

            String blackListString = blackList.getUserId() + "\t"
                    + blackList.getStartDate().format(DATE_FORMATTER)+ "\t"
                    + (blackList.getEndDate() != null ? blackList.getEndDate().format(DATE_FORMATTER): "null");
            writer.write(blackListString);
            writer.newLine();

            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("블랙리스트 추가 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    /**
     * 블랙리스트를 삭제한다.
     * */
    public void removeBlackList(BlackList removedBlackList){
        List<BlackList> blackLists = loadAllBlackList();
        boolean isUpdated = false;

        for (int i = 0; i < blackLists.size(); i++) {
            if (blackLists.get(i).getUserId().equals(removedBlackList.getUserId())) {
                blackLists.remove(i);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/blacklist.txt")));

                for (BlackList blackList : blackLists) {
                    String blackListString = blackList.getUserId() + "\t"
                            + blackList.getStartDate().format(DATE_FORMATTER)+ "\t"
                            + (blackList.getEndDate().equals("null") ? blackList.getEndDate().format(DATE_FORMATTER): "null");
                    writer.write(blackListString);
                    writer.newLine();

                    writer.flush();
                    writer.close();
                }

                writer.flush();
                writer.close();
            } catch (IOException e) {
                System.out.println("블랙리스트 삭제 중 오류가 발생했습니다.");
                e.printStackTrace();
            }
        } else {
            System.out.println("해당 블랙리스트를 찾을 수 없습니다.");
        }
    }
}
