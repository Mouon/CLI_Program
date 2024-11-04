package org.example.service;

import org.example.domain.Setting;
import org.example.file.SettingFileManager;

public class SettingService {
    public SettingFileManager settingFileManager;

    public SettingService(SettingFileManager settingFileManager) {
        this.settingFileManager = settingFileManager;
    }


    /**
     * 반납기간 변경하기
     * */
    public void changeCheckoutDuration(int newDuration){
        Setting newSetting = settingFileManager.loadSettingByName("checkoutDuration");
        newSetting.setValue(Integer.toString(newDuration));
        settingFileManager.updateSetting(newSetting);
    }

    /**
     * setting에 있는 반납기간 값 가져오기
     */
    public int getCheckoutDuration(){
        return Integer.parseInt(settingFileManager.getValueBySettingName("checkoutDuration"));
    }
}
