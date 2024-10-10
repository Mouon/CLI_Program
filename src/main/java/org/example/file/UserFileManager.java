package org.example.file;

import org.example.domain.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserFileManager {
    public UserFileManager() {
    }

    /**
     * 전체 유저를 로드한다.
     * */
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
     * @ 시용 예시: 회원 있고, 이름 얻을때
     *     userFileManager.loadUserById("user1234").getUserName()
     *
     * @ 시용 예시 2: 회원 없을때
     *     try {
     *             System.out.println(userFileManager.loadUserById("use34").getUserName());
     *
     *         }catch (NullPointerException e){
     *             System.out.println("회원이 없습니다.");
     *         }
     * */
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
     * 새로운 유저를 파일에 추가하는 메서드 (join)
     * @ 사용법 : 저장할때
     *         User user = new User("사용자","mhj","122321","건구스");
     *         userFileManager.join(user);
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
            System.out.println("유저 추가 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    /**
     * 유저 정보를 수정하는 메서드
     * 기존 파일에서 특정 유저 정보를 찾아 수정한 후 파일에 다시 저장
     *
     * @ 사용법 :
     *         User user = new User("사용자","user1234","password123!","건구스몽");
     *         userFileManager.updateUser(user);
     *
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
                System.out.println("유저 정보 업데이트 중 오류가 발생했습니다.");
                e.printStackTrace();
            }
        } else {
            System.out.println("해당 유저를 찾을 수 없습니다.");
        }
    }


}
