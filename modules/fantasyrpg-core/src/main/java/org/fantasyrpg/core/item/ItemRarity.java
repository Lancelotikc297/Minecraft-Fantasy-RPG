package org.fantasyrpg.core.item;

/**
 * Градации редкости предметов согласно разделу 5 мастер-спецификации.
 */
public enum ItemRarity {
    COMMON("common", "Обычный", "#FFFFFF", 1.0),
    UNCOMMON("uncommon", "Необычный", "#55FF55", 1.2),
    RARE("rare", "Редкий", "#5555FF", 1.5),
    EPIC("epic", "Эпический", "#AA00AA", 1.9),
    LEGENDARY("legendary", "Легендарный", "#FFAA00", 2.4),
    MYTHIC("mythic", "Артефактный", "#AA0000", 3.0);

    private final String id;
    private final String russianName;
    private final String hexColor;
    private final double statMultiplier;

    ItemRarity(String id, String russianName, String hexColor, double statMultiplier) {
        this.id = id;
        this.russianName = russianName;
        this.hexColor = hexColor;
        this.statMultiplier = statMultiplier;
    }

    public String getId() {
        return id;
    }

    public String getRussianName() {
        return russianName;
    }

    public String getHexColor() {
        return hexColor;
    }

    public double getStatMultiplier() {
        return statMultiplier;
    }

    public static ItemRarity fromString(String id) {
        if (id == null) return COMMON;
        for (ItemRarity r : values()) {
            if (r.id.equalsIgnoreCase(id) || r.name().equalsIgnoreCase(id)) {
                return r;
            }
        }
        return COMMON;
    }
}
