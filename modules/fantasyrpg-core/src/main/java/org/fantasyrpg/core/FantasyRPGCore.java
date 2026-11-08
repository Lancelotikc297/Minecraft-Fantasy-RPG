package org.fantasyrpg.core;

import org.fantasyrpg.core.config.RPGConfig;
import org.fantasyrpg.core.data.RPGDataManager;

/**
 * Главная точка входа Forge мода FantasyRPG Core (modId: fantasyrpg_core).
 */
public class FantasyRPGCore {

    public static final String MOD_ID = "fantasyrpg_core";
    public static final String MOD_NAME = "FantasyRPG Core";
    public static final String VERSION = "1.0.0";

    private static FantasyRPGCore instance;
    private final RPGConfig config;

    public FantasyRPGCore() {
        instance = this;
        this.config = new RPGConfig();
        init();
    }

    public static FantasyRPGCore getInstance() {
        if (instance == null) {
            instance = new FantasyRPGCore();
        }
        return instance;
    }

    private void init() {
        System.out.printf("[%s] Initializing FantasyRPG Core v%s (Target: Minecraft 1.20.1 Forge)%n", MOD_NAME, VERSION);
        System.out.printf("[%s] Player builds protection against dragons: %b%n", MOD_NAME, config.isProtectPlayerBuilds());
        System.out.printf("[%s] Active language: %s%n", MOD_NAME, config.getLanguage());
        System.out.printf("[%s] Active performance profile: %s%n", MOD_NAME, config.getPerformanceProfile());
    }

    public RPGConfig getConfig() {
        return config;
    }

    public RPGDataManager getDataManager() {
        return RPGDataManager.getInstance();
    }
}
