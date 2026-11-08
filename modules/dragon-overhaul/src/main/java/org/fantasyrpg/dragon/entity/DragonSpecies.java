package org.fantasyrpg.dragon.entity;

/**
 * Разновидности драконов в соответствии с разделом 6.1 спецификации.
 * Сохраняются все оригинальные виды: Огненный, Ледяной, Громовой.
 */
public enum DragonSpecies {
    FIRE("fire", "Огненный дракон", "Дышит разрушительным пламенем, обитает в теплых биомах и горах"),
    ICE("ice", "Ледяной дракон", "Выдыхает ледяной мороз, обитает в снежных тундрах и ледниках"),
    LIGHTNING("lightning", "Громовой дракон", "Поражает врагов разрядами молний, обитает в саваннах и грозовых пустошах");

    private final String id;
    private final String russianName;
    private final String description;

    DragonSpecies(String id, String russianName, String description) {
        this.id = id;
        this.russianName = russianName;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getRussianName() {
        return russianName;
    }

    public String getDescription() {
        return description;
    }
}
