package org.example.service;

import org.example.domain.Setting;
import org.example.file.SettingFileManager;

import java.util.function.ToDoubleBiFunction;

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
     * 블랙리스트 기간 변경하기
     */
    public void changeBlacklistDuration(int newDuration){
        Setting newSetting = settingFileManager.loadSettingByName("blacklistDuration");
        System.out.println("loaded setting val : "+newSetting.getValue());
        newSetting.setValue(Integer.toString(newDuration));
        settingFileManager.updateSetting(newSetting);
    }

    /**
     * 최대 대출 권수 변경하기
     */
    public void changeMaxCheckout(int newMaxCheckout){
        Setting newSetting = settingFileManager.loadSettingByName("maxCheckout");
        newSetting.setValue(Integer.toString(newMaxCheckout));
        settingFileManager.updateSetting(newSetting);
    }

    /**
     * setting에 있는 반납기간 값 가져오기
     */
    public int getCheckoutDuration(){
        return Integer.parseInt(settingFileManager.getValueBySettingName("checkoutDuration"));
    }

    /**
     * setting에 있는 블랙리스트 기간 가져오기
     */
    public int getBlacklistDuration(){
        return Integer.parseInt(settingFileManager.getValueBySettingName("blacklistDuration"));
    }

    /**
     * setting에 있는 최대 대출 권수 가져오기
     */
    public int getMaxCheckout(){
        return Integer.parseInt(settingFileManager.getValueBySettingName("maxCheckout"));
    }

}
