package org.fantasyrpg.core.data;

/**
 * Перечисление ролевых классов персонажа.
 * Доступные классы согласованы с Разделом 3 мастер-спецификации.
 */
public enum RPGClass {
    NONE("none", "Без класса"),
    WARRIOR("warrior", "Воин"),
    PALADIN("paladin", "Паладин"),
    MAGE("mage", "Маг"),
    NECROMANCER("necromancer", "Некромант"),
    ASSASSIN("assassin", "Ассасин"),
    ARCHER("archer", "Лучник");

    private final String id;
    private final String russianName;

    RPGClass(String id, String russianName) {
        this.id = id;
        this.russianName = russianName;
    }

    public String getId() {
        return id;
    }

    public String getRussianName() {
        return russianName;
    }

    public static RPGClass fromString(String text) {
        if (text == null) return NONE;
        for (RPGClass c : values()) {
            if (c.id.equalsIgnoreCase(text) || c.name().equalsIgnoreCase(text)) {
                return c;
            }
        }
        return NONE;
    }
}
