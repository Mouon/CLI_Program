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
    public void changeCheckoutDuration(String newDuration){
        Setting newSetting = settingFileManager.loadSettingByName("checkoutDuration");
        newSetting.setValue(newDuration);
        settingFileManager.updateSetting(newSetting);
    }
}
