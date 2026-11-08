package org.fantasyrpg.core.config;

import java.util.Collections;
import java.util.List;

/**
 * Конфигурация модуля FantasyRPG Core.
 * Реализует обязательные требования мастер-спецификации:
 * - защита построек игрока от драконов по умолчанию (protectPlayerBuilds = true)
 * - русский язык по умолчанию
 * - профили производительности
 * - строгий запрет запрещенных школ магии
 */
public class RPGConfig {

    private boolean protectPlayerBuilds = true;
    private String language = "ru_ru";
    private String performanceProfile = "balanced";
    private boolean starterGuidebook = true;
    private int baseLevelXP = 100;
    private double xpScalingFactor = 1.5;
    private List<String> enabledMagicSchools = List.of("fire", "ice", "lightning", "dark", "holy");
    private List<String> forbiddenMagicSchools = List.of("illusion", "spatial_teleport");

    public RPGConfig() {
    }

    public boolean isProtectPlayerBuilds() {
        return protectPlayerBuilds;
    }

    public void setProtectPlayerBuilds(boolean protectPlayerBuilds) {
        // Защита построек по умолчанию является обязательным требованием ТЗ (Section 6.2)
        this.protectPlayerBuilds = protectPlayerBuilds;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPerformanceProfile() {
        return performanceProfile;
    }

    public void setPerformanceProfile(String performanceProfile) {
        this.performanceProfile = performanceProfile;
    }

    public boolean isStarterGuidebook() {
        return starterGuidebook;
    }

    public void setStarterGuidebook(boolean starterGuidebook) {
        this.starterGuidebook = starterGuidebook;
    }

    public int getBaseLevelXP() {
        return baseLevelXP;
    }

    public void setBaseLevelXP(int baseLevelXP) {
        this.baseLevelXP = baseLevelXP;
    }

    public double getXpScalingFactor() {
        return xpScalingFactor;
    }

    public void setXpScalingFactor(double xpScalingFactor) {
        this.xpScalingFactor = xpScalingFactor;
    }

    public List<String> getEnabledMagicSchools() {
        return Collections.unmodifiableList(enabledMagicSchools);
    }

    public List<String> getForbiddenMagicSchools() {
        return Collections.unmodifiableList(forbiddenMagicSchools);
    }

    public boolean isSchoolAllowed(String school) {
        if (school == null) return false;
        String s = school.toLowerCase().trim();
        if (forbiddenMagicSchools.contains(s)) return false;
        return enabledMagicSchools.contains(s);
    }
}
