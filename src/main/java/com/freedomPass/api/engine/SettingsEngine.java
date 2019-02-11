package com.freedomPass.api.engine;

import com.freedomPass.project.model.SettingsMapping;
import com.freedomPass.project.service.GetSettingsService;
import com.freedomPass.project.service.SettingsMappingService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SettingsEngine {

    public boolean initialized = false;

    private final Map<String, Object> settingsMapping = new HashMap<>();

    private List<SettingsMapping> settingsMappingList = new ArrayList<>();

    @Autowired
    SettingsMappingService settingsMappingService;

    @Autowired
    GetSettingsService getsettingsservice;

    @Scheduled(fixedDelay = 86400000, initialDelay = 30000)
    public synchronized void init() {
        if (!initialized) {
            this.loadSettings();
        }
    }

    public void loadSettings() {
        settingsMapping.clear();
        settingsMappingList = settingsMappingService.getSettingsDetails((long) 1, "all", 1, "en");
        for (SettingsMapping settingMapping : settingsMappingList) {
            switch (settingMapping.getFieldType()) {
                case "NUMBER":
                    settingsMapping.put(settingMapping.getColumnName().toLowerCase(), Long.parseLong(settingMapping.getColumnValue()));
                    break;
                case "TEXT":
                    settingsMapping.put(settingMapping.getColumnName().toLowerCase(), settingMapping.getColumnValue());
                    break;
                case "CHECKBOX":
                    settingsMapping.put(settingMapping.getColumnName().toLowerCase(), Boolean.parseBoolean(settingMapping.getColumnValue()));
                    break;
                default:
                    settingsMapping.put(settingMapping.getColumnName().toLowerCase(), settingMapping.getColumnValue());
                    break;
            }
        }
        initialized = true;
    }

    public Object getFirstLevelSetting(String settingName) {
        if (!initialized) {
            loadSettings();
        }
        return settingsMapping.get((settingName.toLowerCase()));
    }

    public Map<String, Object> getSettingssMapping() {
        return settingsMapping;
    }

}
