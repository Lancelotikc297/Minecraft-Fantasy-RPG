package org.fantasyrpg.core.item;

import java.util.*;

/**
 * Реестр зарегистрированных предметов, оружия и монет RPG Core.
 */
public class RPGItemRegistry {

    private static final RPGItemRegistry INSTANCE = new RPGItemRegistry();
    private final Map<String, RPGItemDefinition> items = new LinkedHashMap<>();

    private RPGItemRegistry() {
        registerDefaultItems();
    }

    public static RPGItemRegistry getInstance() {
        return INSTANCE;
    }

    private void registerDefaultItems() {
        // Универсальная валюта
        register(new RPGItemDefinition("copper_coin", "Медная монета", "Copper Coin",
                "Базовая разменная монета королевств. 100 меди = 1 серебро.", ItemRarity.COMMON, 64, 0, 0, "none"));

        register(new RPGItemDefinition("silver_coin", "Серебряная монета", "Silver Coin",
                "Ценная торговая валюта купцов и гильдий. 100 серебра = 1 золото.", ItemRarity.UNCOMMON, 64, 0, 0, "none"));

        register(new RPGItemDefinition("gold_coin", "Золотая монета", "Gold Coin",
                "Редкая имперская золотая монета великих правителей.", ItemRarity.RARE, 64, 0, 0, "none"));

        // Ролевое оружие
        register(new RPGItemDefinition("flame_blade", "Огненный клинок драконоборца", "Dragonslayer Flame Blade",
                "Клинок, пылающий драконьим пламенем.", ItemRarity.EPIC, 1, 14, 1.6, "fire"));

        register(new RPGItemDefinition("frost_dagger", "Ледяной кинжал стужи", "Frostbite Dagger",
                "Острый кинжал из вечного льда.", ItemRarity.RARE, 1, 9, 2.2, "ice"));

        register(new RPGItemDefinition("thunder_mace", "Громовой молот титана", "Titan Thunder Mace",
                "Тяжелый молот, сотрясающий землю ударами молний.", ItemRarity.LEGENDARY, 1, 18, 1.1, "lightning"));

        register(new RPGItemDefinition("shadow_scythe", "Коса жнеца душ", "Soul Reaper Scythe",
                "Оружие некроманта, питающееся жизненной силой павших врагов.", ItemRarity.EPIC, 1, 15, 1.4, "dark"));

        register(new RPGItemDefinition("holy_warhammer", "Священный боевой молот", "Holy Warhammer",
                "Освященное оружие паладина, сокрушающее порождения тьмы.", ItemRarity.EPIC, 1, 16, 1.2, "holy"));

        // Аксессуары
        register(new RPGItemDefinition("ring_of_vitality", "Кольцо жизненной силы", "Ring of Vitality",
                "Древнее кольцо, повышающее максимальное здоровье носителя.", ItemRarity.RARE, 1, 0, 0, "none"));

        register(new RPGItemDefinition("amulet_of_wisdom", "Амулет мудрости", "Amulet of Wisdom",
                "Магический амулет, ускоряющий восстановление маны.", ItemRarity.RARE, 1, 0, 0, "none"));

        register(new RPGItemDefinition("cloak_of_shadows", "Плащ теней", "Cloak of Shadows",
                "Шелковый темный плащ, увеличивающий шанс уклонения от ударов.", ItemRarity.EPIC, 1, 0, 0, "none"));

        // Руководство
        register(new RPGItemDefinition("guidebook_item", "Книга знаний о мире", "Fantasy RPG Guidebook",
                "Руководство искателя приключений.", ItemRarity.UNCOMMON, 1, 0, 0, "none"));
    }

    public void register(RPGItemDefinition item) {
        items.put(item.getId().toLowerCase(), item);
    }

    public RPGItemDefinition getItem(String id) {
        if (id == null) return null;
        return items.get(id.toLowerCase());
    }

    public Collection<RPGItemDefinition> getAllItems() {
        return Collections.unmodifiableCollection(items.values());
    }
}
