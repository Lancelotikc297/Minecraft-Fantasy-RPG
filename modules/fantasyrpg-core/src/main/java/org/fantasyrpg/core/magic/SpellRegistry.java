package org.fantasyrpg.core.magic;

import org.fantasyrpg.core.data.PlayerRPGData;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Реестр заклинаний и менеджер сотворения магии.
 */
public class SpellRegistry {

    private static final SpellRegistry INSTANCE = new SpellRegistry();
    private final Map<String, Spell> spells = new LinkedHashMap<>();
    private final Map<UUID, Map<String, Long>> cooldownTimers = new ConcurrentHashMap<>();

    private SpellRegistry() {
        registerDefaultSpells();
    }

    public static SpellRegistry getInstance() {
        return INSTANCE;
    }

    private void registerDefaultSpells() {
        // Магия огня (Fire)
        register(new Spell("fireball", "Огненный шар", "Выпускает сгусток пламени, взрывающийся при попадании", SpellSchool.FIRE, 20, 3, 25, 10));
        register(new Spell("flame_wave", "Волна пламени", "Сжигает врагов в конусе перед заклинателем", SpellSchool.FIRE, 35, 8, 40, 14));
        register(new Spell("ignite", "Воспламенение", "Поджигает цель, нанося периодический огненный урон", SpellSchool.FIRE, 15, 5, 18, 12));

        // Магия льда (Ice)
        register(new Spell("ice_spike", "Ледяной шип", "Пронзает врага острым ледяным осколком и замедляет его", SpellSchool.ICE, 18, 2, 20, 10));
        register(new Spell("frost_nova", "Кольцо холода", "Замораживает всех ближайших врагов на несколько секунд", SpellSchool.ICE, 30, 10, 25, 13));
        register(new Spell("blizzard", "Буран", "Вызывает снежную бурю, наносящую непрерывный урон холодом", SpellSchool.ICE, 50, 20, 55, 16));

        // Магия молнии (Lightning)
        register(new Spell("lightning_bolt", "Удар молнии", "Мгновенно поражает цель разрядом молнии с неба", SpellSchool.LIGHTNING, 25, 4, 30, 11));
        register(new Spell("chain_lightning", "Цепная молния", "Электрический разряд перескакивает между несколькими врагами", SpellSchool.LIGHTNING, 40, 9, 35, 15));

        // Темная магия (Dark)
        register(new Spell("shadow_bolt", "Стрела тьмы", "Сгусток темной энергии, игнорирующий часть брони цели", SpellSchool.DARK, 22, 3, 26, 12));
        register(new Spell("curse_of_agony", "Проклятие мучений", "Ослабляет защиту цели и вытягивает жизненную силу", SpellSchool.DARK, 35, 12, 32, 14));

        // Священная магия (Holy)
        register(new Spell("heal", "Исцеление", "Восстанавливает здоровье заклинателю или союзнику", SpellSchool.HOLY, 25, 6, 0, 11));
        register(new Spell("holy_radiance", "Священное сияние", "Ослепляет нежить и очищает союзников от негативных эффектов", SpellSchool.HOLY, 35, 15, 28, 13));
    }

    public boolean register(Spell spell) {
        if (spell == null) return false;
        if (!spell.getSchool().isAllowedInGame()) {
            // Строго запрещено регистрировать заклинания иллюзий и порталов согласно разделу 4
            System.err.printf("[SpellRegistry] REJECTED forbidden spell school: %s for spell %s%n",
                    spell.getSchool(), spell.getId());
            return false;
        }
        spells.put(spell.getId().toLowerCase(), spell);
        return true;
    }

    public Spell getSpell(String id) {
        if (id == null) return null;
        return spells.get(id.toLowerCase());
    }

    public Collection<Spell> getAllSpells() {
        return Collections.unmodifiableCollection(spells.values());
    }

    public List<Spell> getSpellsBySchool(SpellSchool school) {
        List<Spell> list = new ArrayList<>();
        for (Spell s : spells.values()) {
            if (s.getSchool() == school) {
                list.add(s);
            }
        }
        return list;
    }

    public CastResult castSpell(PlayerRPGData caster, String spellId, long currentTimeMillis) {
        Spell spell = getSpell(spellId);
        if (spell == null) {
            return new CastResult(false, 0, "Неизвестное заклинание!");
        }

        if (caster.getIntelligence() < spell.getMinIntelligence()) {
            return new CastResult(false, 0, String.format(
                    "Недостаточно интеллекта для сотворения заклинания! Требуется: %d", spell.getMinIntelligence()));
        }

        if (caster.getCurrentMana() < spell.getManaCost()) {
            return new CastResult(false, 0, String.format(
                    "Недостаточно маны! Требуется: %d (Текущая: %d)", spell.getManaCost(), caster.getCurrentMana()));
        }

        // Проверка времени восстановления (cooldown)
        Map<String, Long> playerCooldowns = cooldownTimers.computeIfAbsent(caster.getPlayerUUID(), k -> new ConcurrentHashMap<>());
        Long lastCastTime = playerCooldowns.get(spell.getId());
        if (lastCastTime != null) {
            long remainingSec = (lastCastTime + (spell.getCooldownSeconds() * 1000L) - currentTimeMillis) / 1000;
            if (remainingSec > 0) {
                return new CastResult(false, 0, String.format(
                        "Заклинание перезаряжается! Осталось %d сек.", remainingSec));
            }
        }

        // Успешный каст
        caster.setCurrentMana(caster.getCurrentMana() - spell.getManaCost());
        playerCooldowns.put(spell.getId(), currentTimeMillis);
        int finalDamage = spell.calculateDamage(caster);

        return new CastResult(true, finalDamage, String.format(
                "Сотворено заклинание [%s]! Нанесено урона: %d. Остаток маны: %d/%d",
                spell.getRussianName(), finalDamage, caster.getCurrentMana(), caster.getMaxMana()));
    }

    public void clearCooldowns() {
        cooldownTimers.clear();
    }

    public static class CastResult {
        private final boolean success;
        private final int damage;
        private final String message;

        public CastResult(boolean success, int damage, String message) {
            this.success = success;
            this.damage = damage;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public int getDamage() {
            return damage;
        }

        public String getMessage() {
            return message;
        }
    }
}
