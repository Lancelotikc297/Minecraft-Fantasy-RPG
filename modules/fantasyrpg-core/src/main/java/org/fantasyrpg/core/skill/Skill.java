package org.fantasyrpg.core.skill;

import org.fantasyrpg.core.data.RPGClass;

/**
 * Описание ролевого навыка или способности.
 */
public class Skill {

    private final String id;
    private final String russianName;
    private final String description;
    private final RPGClass requiredClass;
    private final int requiredLevel;
    private final int staminaCost;
    private final int cooldownSeconds;

    public Skill(String id, String russianName, String description, RPGClass requiredClass, int requiredLevel, int staminaCost, int cooldownSeconds) {
        this.id = id;
        this.russianName = russianName;
        this.description = description;
        this.requiredClass = requiredClass;
        this.requiredLevel = requiredLevel;
        this.staminaCost = staminaCost;
        this.cooldownSeconds = cooldownSeconds;
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

    public RPGClass getRequiredClass() {
        return requiredClass;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public int getStaminaCost() {
        return staminaCost;
    }

    public int getCooldownSeconds() {
        return cooldownSeconds;
    }
}
