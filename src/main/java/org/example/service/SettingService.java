package org.example.service;

import org.example.domain.BlackList;
import org.example.domain.Setting;
import org.example.dto.LoginMember;
import org.example.file.BlackListFileManager;
import org.example.file.SettingFileManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

public class SettingService {
    public SettingFileManager settingFileManager;
    public BlackListFileManager blackListFileManager;

    public SettingService(SettingFileManager settingFileManager, BlackListFileManager blackListFileManager) {
        this.settingFileManager = settingFileManager;
        this.blackListFileManager = blackListFileManager;
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
        newSetting.setValue(Integer.toString(newDuration));
        settingFileManager.updateSetting(newSetting);

        //blacklist.txt에 있는 변경 시점 이후에 blacklist에 추가된 데이터들에 대하여 종료 기간 변경
        List<BlackList> allBlackList = blackListFileManager.loadAllBlackList();
        List<BlackList> updatedBlackList = new ArrayList<>();
        int blackListDuration = getBlacklistDuration();

        //변경할 blackList 찾으면
        for(BlackList blackList : allBlackList){
            //blacklist에 추가된 날짜가 현재 로그인 시간과 같거나 이후이면 기간 업데이트
            if(!blackList.getStartDate().isBefore(LoginMember.getLoginTime())){
                LocalDate startDate = blackList.getStartDate();
                //blacklist 종료일을 현재 시작일 + 블랙리스트 기간으로 설정
                blackList.setEndDate(startDate.plusDays(blackListDuration));
                updatedBlackList.add(blackList);
            }
        }

        //변경할 blackList 정보들 update
        for(BlackList blackList : updatedBlackList){
            blackListFileManager.updateBlackList(blackList);
        }
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
