package org.fantasyrpg.core.magic;

/**
 * Школы магии.
 * Строго соблюдает правила Раздела 4 мастер-спецификации:
 * - разрешены: Огонь, Лед, Молния, Тьма, Священная магия
 * - запрещены: Иллюзии, Порталы / Пространственная магия
 */
public enum SpellSchool {
    FIRE("fire", "Магия огня", true),
    ICE("ice", "Магия льда", true),
    LIGHTNING("lightning", "Магия молнии", true),
    DARK("dark", "Темная магия", true),
    HOLY("holy", "Священная магия", true),
    ILLUSION("illusion", "Магия иллюзий", false),
    SPATIAL_PORTAL("spatial_portal", "Пространственная магия", false);

    private final String id;
    private final String russianName;
    private final boolean allowedInGame;

    SpellSchool(String id, String russianName, boolean allowedInGame) {
        this.id = id;
        this.russianName = russianName;
        this.allowedInGame = allowedInGame;
    }

    public String getId() {
        return id;
    }

    public String getRussianName() {
        return russianName;
    }

    public boolean isAllowedInGame() {
        return allowedInGame;
    }

    public static SpellSchool fromString(String id) {
        if (id == null) return null;
        for (SpellSchool s : values()) {
            if (s.id.equalsIgnoreCase(id) || s.name().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }
}
