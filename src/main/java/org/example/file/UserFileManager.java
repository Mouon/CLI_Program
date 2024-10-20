package org.example.file;

import org.example.domain.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @class UserFileManager
 * @description 사용자 정보 관련 파일 작업을 관리하는 클래스
 *
 * 이 클래스는 사용자 데이터의 로드, 추가, 업데이트 등의 기능을 제공합니다.
 * 사용자 정보는 파일 시스템에 저장되며, 이 클래스를 통해 관리됩니다.
 */
public class UserFileManager {
    public UserFileManager() {
    }

    /**
     * 전체 사용자 목록을 로드한다.
     *
     * @return 모든 사용자 정보를 포함하는 List<User>
     */
    public List<User> loadUserList() {
        List<User> userList = new ArrayList<>();
        try {
            Scanner file = new Scanner(new File("src/main/resources/users.txt"));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                userList.add(new User(result[0].trim(), result[1].trim(), result[2].trim(), result[3].trim()));
            }
            return userList;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     * 사용자 ID로 특정 사용자 정보를 로드한다.
     *
     * @param userId 검색할 사용자의 ID
     * @return 해당 ID의 User 객체, 없으면 null
     *
     * 사용 예시 1: 회원이 있고, 이름을 얻을 때
     * userFileManager.loadUserById("user1234").getUserName()
     *
     * 사용 예시 2: 회원이 없을 때
     * try {
     *     System.out.println(userFileManager.loadUserById("use34").getUserName());
     * } catch (NullPointerException e) {
     *     System.out.println("회원이 없습니다.");
     * }
     */
    public User loadUserById(String userId) {
        try {
            Scanner file = new Scanner(new File("src/main/resources/users.txt"));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                if(result[1].trim().equals(userId)){
                    return new User(result[0].trim(), result[1].trim(), result[2].trim(), result[3].trim());
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     * 새로운 사용자를 파일에 추가하는 메서드 (회원가입)
     *
     * @param user 추가할 User 객체
     *
     * 사용 예시:
     * User user = new User("사용자", "mhj", "122321", "건구스");
     * userFileManager.join(user);
     */
    public void join(User user) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/users.txt"), true));

            String userString = user.getUserType() + "\t" + user.getUserId() + "\t" + user.getPassword() + "\t" + user.getUserName();
            writer.write(userString);
            writer.newLine();

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 사용자 정보를 수정하는 메서드
     * 기존 파일에서 특정 사용자 정보를 찾아 수정한 후 파일에 다시 저장한다.
     *
     * @param updatedUser 업데이트할 User 객체
     *
     * 사용 예시:
     * User user = new User("사용자", "user1234", "password123!", "건구스몽");
     * userFileManager.updateUser(user);
     */
    public void updateUser(User updatedUser) {
        List<User> userList = loadUserList();
        boolean isUpdated = false;

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId().equals(updatedUser.getUserId())) {
                userList.set(i, updatedUser);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/users.txt")));

                for (User user : userList) {
                    String userString = user.getUserType() + "\t" + user.getUserId() + "\t" + user.getPassword() + "\t" + user.getUserName();
                    writer.write(userString);
                    writer.newLine();
                }

                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("해당 유저를 찾을 수 없습니다.");
        }
    }


}
