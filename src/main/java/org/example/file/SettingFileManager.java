package org.example.file;

import org.example.domain.Setting;
import org.example.domain.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SettingFileManager {
    public static final String FILE_PATH = "src/main/resources/setting.txt";

    /**
     * 전체 사용자 목록을 로드한다.
     *
     * @return 모든 사용자 정보를 포함하는 List<User>
     */
    public List<Setting> loadSettingList() {
        List<Setting> settingList = new ArrayList<>();
        try {
            Scanner file = new Scanner(new File(FILE_PATH));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                settingList.add(new Setting(result[0].trim(), result[1].trim()));
            }
            return settingList;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     *
     * @param settingName 조회할 셋팅값의 이름
     * @return settingName에 해당하는 값(String)
     */
    public String getValueBySettingName(String settingName) {
        try {
            Scanner file = new Scanner(new File(FILE_PATH));
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                if(result[0].trim().equals(settingName)){
                    return result[1].trim();
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }

    /**
     * @description 셋팅을 저장하는 매서세
     *
     * @param setting 추가할 Setting 객체
     */
    public void save(Setting setting) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILE_PATH), true));

            String settingString = setting.getSettingName() + "\t" + setting.getValue();
            writer.write(settingString);
            writer.newLine();

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 셋팅 정보를 수정하는 메서드 기존 파일에서 특정 셋팅 정보를 찾아 수정한 후 파일에 다시 저장한다.
     *
     * @param updatedSetting 업데이트할 Setting 객체
     *
     */
    public void updateSetting(Setting updatedSetting) {
        List<Setting> settingList = loadSettingList();
        boolean isUpdated = false;

        for (int i = 0; i < settingList.size(); i++) {
            if (settingList.get(i).getSettingName().equals(updatedSetting.getSettingName())) {
                settingList.set(i, updatedSetting);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILE_PATH)));

                for (Setting setting : settingList) {
                    String settingString = setting.getSettingName() + "\t" + setting.getValue();
                    writer.write(settingString);
                    writer.newLine();
                }

                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("해당셋팅을 찾을 수 없습니다.");
        }
    }

    /**
     * @description 셋팅 객체를 불러온다.
     *
     * @param settingName 해당하는 값(String)
     *
     */
    public Setting loadSettingByName(String settingName) {
        try {
            Scanner file = new Scanner(new File(FILE_PATH));
            while (file.hasNext()) {
                String str = file.nextLine();
                System.out.println("str : "+str);
                String[] result = str.split("\t");
                if(result[0].trim().equals(settingName)){
                    System.out.println("in return");
                    return new Setting(result[0].trim(), result[1].trim());
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다.");
            throw new RuntimeException();
        }
    }
}
