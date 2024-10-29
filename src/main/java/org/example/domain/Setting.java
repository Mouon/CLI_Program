package org.example.domain;

public class Setting {
    private String settingName;

    private String value;

    public Setting(String settingName, String value) {
        this.settingName = settingName;
        this.value = value;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
