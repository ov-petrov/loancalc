package ru.petrovov.application.backend;

import java.util.Map;

public interface SettingsService {

    Map<String, String> getSettings();

    void refreshSettings();

}
