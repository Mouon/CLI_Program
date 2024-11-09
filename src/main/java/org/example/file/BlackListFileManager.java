package org.example.file;

import org.example.domain.BlackList;
import org.example.domain.Checkout;
import org.example.domain.User;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * @class BlackListFileManager
 * @description 블랙리스트 관련 파일 작업을 관리하는 클래스
 *
 * 이 클래스는 블랙리스트 데이터의 로드, 추가, 삭제, 업데이트 등의 기능을 제공합니다.
 * 블랙리스트 정보는 파일 시스템에 저장되며, 이 클래스를 통해 관리됩니다.
 */
public class BlackListFileManager {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public BlackListFileManager() {
    }

    /**
     * 전체 블랙리스트를 로드한다.
     *
     * @return 모든 블랙리스트 항목을 포함하는 List
     */
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

    /**
     * 특정 사용자 ID에 해당하는 블랙리스트를 로드한다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 해당 사용자의 블랙리스트 항목을 포함하는 List
     *
     * 사용 예:
     * blackListFileManager.loadBlackListByUserId("user1234")
     */
    public List<BlackList> loadBlackListByUserId(String userId) {
        try {
            List<BlackList> blackLists = new ArrayList<>();
            Scanner file = new Scanner(new File("src/main/resources/blacklist.txt"));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                if(result[0].trim().equals(userId)){
                    blackLists.add(new BlackList(result[0].trim(), LocalDate.parse(result[1].trim(),DATE_FORMATTER)
                            ,(result[2].trim().equals("null") ? null : LocalDate.parse(result[2].trim(),DATE_FORMATTER))));
                }
            }
            return blackLists;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     * 해당 사용자가 블랙리스트에 포함되어 있는지 확인한다.
     *
     * @param user 확인할 사용자 객체
     * @param localDate 확인할 날짜
     * @return 블랙리스트 포함 여부 (true: 포함, false: 미포함)
     *
     * 사용 예:
     * UserFileManager userFileManager = new UserFileManager();
     * BlackListFileManager blackListFileManager = new BlackListFileManager();
     * User user = userFileManager.loadUserById("hello123");
     * System.out.println(blackListFileManager.isBlackList(user, LocalDate.now()));
     */
    public boolean isBlackList(User user, LocalDate localDate) {
        try {
            Scanner file = new Scanner(new File("src/main/resources/blacklist.txt"));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");

                if (result[0].trim().equals(user.getUserId())) {
                    LocalDate startDate = LocalDate.parse(result[1].trim(), DATE_FORMATTER);

                    // endDate가 null인 경우 처리
                    if (result[2].trim().equals("null")) {
                        if (localDate.isEqual(startDate) || localDate.isAfter(startDate)) {
                            file.close();
                            return true;
                        }
                        continue;
                    }

                    LocalDate endDate = LocalDate.parse(result[2].trim(), DATE_FORMATTER);

                    // 현재 날짜가 시작일과 종료일 사이에 있는지 확인
                    if ((localDate.isEqual(startDate) || localDate.isAfter(startDate))
                            && (localDate.isEqual(endDate) || localDate.isBefore(endDate))) {
                        file.close();
                        return true;
                    }
                }
            }
            file.close();
            return false;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }
    /**
     * 새로운 블랙리스트 항목을 추가한다.
     *
     * @param blackList 추가할 BlackList 객체
     */

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
     * 블랙리스트 항목을 삭제한다.
     *
     * @param removedBlackList 삭제할 BlackList 객체
     */
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
                            + (blackList.getEndDate().equals("null") ? "null" : blackList.getEndDate().format(DATE_FORMATTER));
                    writer.write(blackListString);
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
     * 블랙리스트 항목을 업데이트한다.
     *
     * @param updatedBlackList 업데이트할 BlackList 객체
     *
     * 사용 예:
     * BlackListFileManager blackListFileManager = new BlackListFileManager();
     * BlackList blackList = blackListFileManager.loadBlackListByUserId("user1234");
     * blackList.setEndDate(LocalDate.now());
     * blackListFileManager.updateBlack(blackList);
     * System.out.println(blackList.getEndDate());
     */
    public void updateBlack(BlackList updatedBlackList){
        List<BlackList> blackLists = loadAllBlackList();
        boolean isUpdated = false;

        for (int i = 0; i < blackLists.size(); i++) {
                blackLists.set(i, updatedBlackList);
                isUpdated = true;
                break;
        }

        if (isUpdated) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/blacklist.txt")));

                for (BlackList blackList : blackLists) {
                    String blackListString = blackList.getUserId() + "\t"
                            + blackList.getStartDate().format(DATE_FORMATTER)+ "\t"
                            + (blackList.getEndDate().equals("null") ? "null" : blackList.getEndDate().format(DATE_FORMATTER));
                    writer.write(blackListString);
                    writer.newLine();
                }

                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("해당 파일을 찾을 수 없습니다.");
        }
    }
}
