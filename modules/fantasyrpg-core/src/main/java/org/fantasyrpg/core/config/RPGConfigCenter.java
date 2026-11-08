package org.fantasyrpg.core.config;

import java.util.*;

/**
 * Комплексный внутриигровой конфигурационный центр (Phase H).
 * Позволяет настраивать все подсистемы: прогрессию, магию, драконов, графические пресеты, сложность и события.
 * Выполняет живую валидацию правил безопасности (защита построек игрока, запрет запрещенных школ).
 */
public class RPGConfigCenter {

    private static final RPGConfigCenter INSTANCE = new RPGConfigCenter();

    public enum ConfigCategory {
        RPG_PROGRESSION("progression", "Ролевая прогрессия"),
        MAGIC_SYSTEM("magic", "Магические школы"),
        DRAGONS_AND_BUILDINGS("dragons", "Драконы и защита построек"),
        COMBAT_DIFFICULTY("combat", "Боевая система и сложность"),
        GRAPHICS_AND_PERFORMANCE("graphics", "Графика и производительность"),
        EVENTS_AND_SPAWN("events", "События мира и спавн");

        private final String id;
        private final String russianTitle;

        ConfigCategory(String id, String russianTitle) {
            this.id = id;
            this.russianTitle = russianTitle;
        }

        public String getId() {
            return id;
        }

        public String getRussianTitle() {
            return russianTitle;
        }
    }

    private final Map<String, Object> settings = new LinkedHashMap<>();

    private RPGConfigCenter() {
        loadDefaultSettings();
    }

    public static RPGConfigCenter getInstance() {
        return INSTANCE;
    }

    private void loadDefaultSettings() {
        // Прогрессия
        settings.put("progression.xp_multiplier", 1.0);
        settings.put("progression.keep_inventory", true);
        settings.put("progression.starter_pouch", true);

        // Магия
        settings.put("magic.school_fire", true);
        settings.put("magic.school_ice", true);
        settings.put("magic.school_lightning", true);
        settings.put("magic.school_dark", true);
        settings.put("magic.school_holy", true);
        settings.put("magic.school_illusion", false); // ЗАПРЕЩЕНО
        settings.put("magic.school_spatial", false);  // ЗАПРЕЩЕНО

        // Драконы
        settings.put("dragons.protect_player_builds", true); // СТРОГО ОБЯЗАТЕЛЬНО
        settings.put("dragons.destruction_mode", "limited");
        settings.put("dragons.amphithere_mounts", true);

        // Графика и производительность (Phase G)
        settings.put("graphics.profile", "balanced"); // low_laptop, balanced, cinematic
        settings.put("graphics.entity_culling", true);
        settings.put("graphics.dynamic_lights", true);
        settings.put("graphics.particle_density", 1.0);

        // События и спавн
        settings.put("events.raid_frequency_days", 5);
        settings.put("events.max_mobs_per_chunk", 16);
    }

    public Object getSetting(String key) {
        return settings.get(key);
    }

    /**
     * Пытается обновить настройку с проверкой правил совместимости и безопасности.
     */
    public boolean updateSetting(String key, Object value) {
        if (key == null || value == null) return false;

        // Валидация 1: Запрещено отключать защиту построек игрока от драконов
        if ("dragons.protect_player_builds".equals(key)) {
            if (Boolean.FALSE.equals(value) || "false".equalsIgnoreCase(value.toString())) {
                System.err.println("[ConfigCenter] Отклонено: защита построек игрока обязательна согласно разделу 6.2!");
                return false;
            }
        }

        // Валидация 2: Запрещено активировать школы иллюзий и пространственных порталов
        if ("magic.school_illusion".equals(key) || "magic.school_spatial".equals(key)) {
            if (Boolean.TRUE.equals(value) || "true".equalsIgnoreCase(value.toString())) {
                System.err.println("[ConfigCenter] Отклонено: школы иллюзий и порталов запрещены согласно разделу 4!");
                return false;
            }
        }

        // Валидация 3: Профили производительности (Phase G)
        if ("graphics.profile".equals(key)) {
            String prof = value.toString().toLowerCase();
            if (!List.of("low_laptop", "balanced", "cinematic").contains(prof)) {
                return false;
            }
        }

        settings.put(key, value);
        return true;
    }

    /**
     * Отображает русскоязычный текстовый интерфейс меню настроек.
     */
    public String renderCategoryMenu(ConfigCategory category) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== ЦЕНТР КОНФИГУРАЦИИ: %s ===\n", category.getRussianTitle()));
        String prefix = category.getId() + ".";

        for (Map.Entry<String, Object> entry : settings.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                String subKey = entry.getKey().substring(prefix.length());
                sb.append(String.format(" - %-25s = %s\n", subKey, entry.getValue()));
            }
        }
        sb.append("Изменить настройку: /rpg config set <категория.параметр> <значение>");
        return sb.toString();
    }
}
