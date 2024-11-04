package org.example.view.host;

import org.example.dto.Model;
import org.example.service.SettingService;
import org.example.service.validater.ValidationService;
import org.example.view.CustomView;

public class HostChangeCheckoutDurationView implements CustomView {
    public ValidationService validationService;
    public SettingService settingService;

    public HostChangeCheckoutDurationView(ValidationService validationService, SettingService settingService) {
        this.validationService = validationService;
        this.settingService = settingService;
    }

    @Override
    public Model begin(Model model) {
        return null;
    }

    @Override
    public String getUri() {
        return "";
    }
}
