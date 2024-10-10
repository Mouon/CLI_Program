package org.example.file;

import org.example.domain.BlackList;
import org.example.domain.Book;
import org.example.domain.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackListFileManager {
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
                blackLists.add(new BlackList(result[0].trim(), Long.parseLong(result[1].trim())));
            }
            return blackLists;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /** @ 사용법 :
     * blackListFileManager.loadBlackListByUserId("user1234").getOverDueDate()
     * */
    public BlackList loadBlackListByUserId(String userId) {
        try {
            Scanner file = new Scanner(new File("src/main/resources/blacklist.txt"));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                if(result[0].trim().equals(userId)){
                    return new BlackList(result[0].trim(), Long.parseLong(result[1].trim()));
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    public void addBlackList(BlackList blackList) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/blacklist.txt"), true));

            String userString = blackList.getUserId() + "\t" + blackList.getOverDueDate();
            writer.write(userString);
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
                    String userString = blackList.getUserId() + "\t" + blackList.getOverDueDate();
                    writer.write(userString);
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
