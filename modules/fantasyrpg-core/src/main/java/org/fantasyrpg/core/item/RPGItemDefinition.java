package org.fantasyrpg.core.item;

/**
 * Описание регистрируемого предмета RPG.
 */
public class RPGItemDefinition {
    private final String id;
    private final String russianName;
    private final String englishName;
    private final String description;
    private final ItemRarity rarity;
    private final int maxStackSize;
    private final int baseDamage;
    private final double attackSpeed;
    private final String elementalType;

    public RPGItemDefinition(String id, String russianName, String englishName, String description, ItemRarity rarity, int maxStackSize, int baseDamage, double attackSpeed, String elementalType) {
        this.id = id;
        this.russianName = russianName;
        this.englishName = englishName;
        this.description = description;
        this.rarity = rarity;
        this.maxStackSize = maxStackSize;
        this.baseDamage = baseDamage;
        this.attackSpeed = attackSpeed;
        this.elementalType = elementalType;
    }

    public String getId() {
        return id;
    }

    public String getRussianName() {
        return russianName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getDescription() {
        return description;
    }

    public ItemRarity getRarity() {
        return rarity;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public String getElementalType() {
        return elementalType;
    }

    public boolean isWeapon() {
        return baseDamage > 0;
    }

    public int getEffectiveDamage() {
        return (int) Math.round(baseDamage * rarity.getStatMultiplier());
    }
}
