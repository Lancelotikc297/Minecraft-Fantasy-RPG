package org.fantasyrpg.core.data;

import java.util.UUID;

/**
 * Ролевые данные игрока: уровень, очки опыта, класс, характеристики, мана и распределяемые очки.
 */
public class PlayerRPGData {

    private final UUID playerUUID;
    private RPGClass characterClass = RPGClass.NONE;
    private int level = 1;
    private int currentXP = 0;
    private int availableAttributePoints = 0;

    // Базовые атрибуты согласно русскоязычному глоссарию
    private int strength = 10;     // Сила
    private int agility = 10;      // Ловкость
    private int intelligence = 10; // Интеллект
    private int endurance = 10;    // Выносливость

    private int currentMana = 50;

    public PlayerRPGData(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public RPGClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(RPGClass characterClass) {
        this.characterClass = characterClass != null ? characterClass : RPGClass.NONE;
        applyClassStatBonuses();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.max(1, level);
    }

    public int getCurrentXP() {
        return currentXP;
    }

    public void setCurrentXP(int currentXP) {
        this.currentXP = Math.max(0, currentXP);
    }

    public int getAvailableAttributePoints() {
        return availableAttributePoints;
    }

    public void setAvailableAttributePoints(int points) {
        this.availableAttributePoints = Math.max(0, points);
    }

    public int getRequiredXPForNextLevel() {
        return (int) (100 * Math.pow(level, 1.5));
    }

    /**
     * Добавляет опыт и производит повышение уровня.
     */
    public boolean addExperience(int xp) {
        if (xp <= 0) return false;
        this.currentXP += xp;
        boolean leveledUp = false;

        while (this.currentXP >= getRequiredXPForNextLevel()) {
            this.currentXP -= getRequiredXPForNextLevel();
            this.level++;
            this.availableAttributePoints += 3; // +3 очка характеристик за уровень
            this.strength += 1;
            this.agility += 1;
            this.intelligence += 1;
            this.endurance += 1;
            this.currentMana = getMaxMana();
            leveledUp = true;
        }

        return leveledUp;
    }

    /**
     * Распределяет свободные очки характеристик.
     */
    public boolean allocateAttribute(String attribute, int points) {
        if (points <= 0 || points > availableAttributePoints) return false;
        if (attribute == null) return false;

        String attr = attribute.toLowerCase().trim();
        switch (attr) {
            case "strength", "сила" -> strength += points;
            case "agility", "ловкость" -> agility += points;
            case "intelligence", "интеллект" -> intelligence += points;
            case "endurance", "выносливость" -> endurance += points;
            default -> {
                return false;
            }
        }

        availableAttributePoints -= points;
        return true;
    }

    public int getMaxMana() {
        return 30 + (this.intelligence * 4);
    }

    public int getMaxHealthBonus() {
        return Math.max(0, (this.endurance - 10) / 2);
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = Math.min(Math.max(0, currentMana), getMaxMana());
    }

    private void applyClassStatBonuses() {
        switch (characterClass) {
            case WARRIOR -> {
                strength = 14;
                endurance = 13;
                agility = 9;
                intelligence = 8;
            }
            case PALADIN -> {
                strength = 12;
                endurance = 14;
                agility = 8;
                intelligence = 11;
            }
            case MAGE -> {
                strength = 7;
                endurance = 9;
                agility = 9;
                intelligence = 16;
            }
            case NECROMANCER -> {
                strength = 8;
                endurance = 10;
                agility = 9;
                intelligence = 15;
            }
            case ASSASSIN -> {
                strength = 10;
                endurance = 9;
                agility = 15;
                intelligence = 10;
            }
            case ARCHER -> {
                strength = 9;
                endurance = 10;
                agility = 16;
                intelligence = 9;
            }
            case NONE -> {
                strength = 10;
                agility = 10;
                intelligence = 10;
                endurance = 10;
            }
        }
        this.currentMana = getMaxMana();
    }
}
