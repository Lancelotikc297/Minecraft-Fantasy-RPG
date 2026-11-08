package org.fantasyrpg.core.magic;

import org.fantasyrpg.core.data.PlayerRPGData;

/**
 * Заклинание магической системы.
 */
public class Spell {

    private final String id;
    private final String russianName;
    private final String description;
    private final SpellSchool school;
    private final int manaCost;
    private final int cooldownSeconds;
    private final int baseDamage;
    private final int minIntelligence;

    public Spell(String id, String russianName, String description, SpellSchool school, int manaCost, int cooldownSeconds, int baseDamage, int minIntelligence) {
        this.id = id;
        this.russianName = russianName;
        this.description = description;
        this.school = school;
        this.manaCost = manaCost;
        this.cooldownSeconds = cooldownSeconds;
        this.baseDamage = baseDamage;
        this.minIntelligence = minIntelligence;
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

    public SpellSchool getSchool() {
        return school;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getCooldownSeconds() {
        return cooldownSeconds;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getMinIntelligence() {
        return minIntelligence;
    }

    /**
     * Вычисляет итоговый урон заклинания с учетом показателя интеллекта заклинателя.
     */
    public int calculateDamage(PlayerRPGData caster) {
        if (caster == null) return baseDamage;
        double multiplier = 1.0 + ((caster.getIntelligence() - 10) * 0.05);
        return (int) Math.max(1, baseDamage * multiplier);
    }
}
