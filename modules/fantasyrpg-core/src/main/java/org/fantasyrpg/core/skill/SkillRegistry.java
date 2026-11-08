package org.fantasyrpg.core.skill;

import org.fantasyrpg.core.data.PlayerRPGData;
import org.fantasyrpg.core.data.RPGClass;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Реестр навыков и древо способностей средней ветвистости.
 * Согласовано с Разделом 3 мастер-спецификации.
 */
public class SkillRegistry {

    private static final SkillRegistry INSTANCE = new SkillRegistry();
    private final Map<String, Skill> skills = new LinkedHashMap<>();
    private final Map<UUID, Set<String>> playerLearnedSkills = new ConcurrentHashMap<>();

    private SkillRegistry() {
        registerDefaultSkills();
    }

    public static SkillRegistry getInstance() {
        return INSTANCE;
    }

    private void registerDefaultSkills() {
        // Воин (Warrior)
        register(new Skill("shield_bash", "Удар щитом", "Оглушает противника и наносит урон от силы", RPGClass.WARRIOR, 1, 15, 6));
        register(new Skill("battle_cry", "Боевой клич", "Повышает силу атаки союзников и снижает урон", RPGClass.WARRIOR, 3, 25, 20));
        register(new Skill("whirlwind", "Вихрь клинков", "Наносит круговой урон всем окружающим врагам", RPGClass.WARRIOR, 5, 35, 12));

        // Паладин (Paladin)
        register(new Skill("holy_strike", "Священный удар", "Наносит урон светом и исцеляет ближайших союзников", RPGClass.PALADIN, 1, 15, 8));
        register(new Skill("divine_shield", "Божественный щит", "Создает барьер, поглощающий входящий урон", RPGClass.PALADIN, 3, 20, 25));
        register(new Skill("consecration", "Освящение земли", "Наносит продолжительный урон нежити и монстрам", RPGClass.PALADIN, 5, 30, 15));

        // Маг (Mage)
        register(new Skill("arcane_focus", "Концентрация магии", "Увеличивает силу заклинаний и скорость регенерации маны", RPGClass.MAGE, 1, 10, 15));
        register(new Skill("elemental_shield", "Стихийный щит", "Поглощает урон стихиями огня, льда и молнии", RPGClass.MAGE, 3, 20, 20));
        register(new Skill("meteor_call", "Призыв метеора", "Обрушивает пылающий метеор на скопление врагов", RPGClass.MAGE, 5, 40, 30));

        // Некромант (Necromancer)
        register(new Skill("raise_skeleton", "Поднятие скелета", "Призывает скелета-стража на службу некроманту", RPGClass.NECROMANCER, 1, 20, 12));
        register(new Skill("life_drain", "Похищение жизни", "Вытягивает здоровье цели и восстанавливает здоровье заклинателя", RPGClass.NECROMANCER, 3, 25, 10));
        register(new Skill("bone_armor", "Костяная броня", "Окружает некроманта костяными пластинами, снижающими урон", RPGClass.NECROMANCER, 5, 30, 25));

        // Ассасин (Assassin)
        register(new Skill("shadow_step", "Шаг сквозь тень", "Мгновенный рывок за спину цели с шансом критического удара", RPGClass.ASSASSIN, 1, 15, 8));
        register(new Skill("poison_blade", "Отравленный клинок", "Покрывает оружие ядом, наносящим периодический урон", RPGClass.ASSASSIN, 3, 20, 15));
        register(new Skill("smoke_bomb", "Дымовая завеса", "Ослепляет врагов и позволяет скрыться от атак", RPGClass.ASSASSIN, 5, 25, 20));

        // Лучник (Archer)
        register(new Skill("rapid_shot", "Быстрая стрельба", "Выпускает серию из трех стрел с повышенной точностью", RPGClass.ARCHER, 1, 15, 6));
        register(new Skill("explosive_arrow", "Взрывная стрела", "Выстреливает стрелой, взрывающейся при попадании", RPGClass.ARCHER, 3, 25, 12));
        register(new Skill("rain_of_arrows", "Град стрел", "Обрушивает поток стрел в указанную область", RPGClass.ARCHER, 5, 35, 25));
    }

    public void register(Skill skill) {
        skills.put(skill.getId().toLowerCase(), skill);
    }

    public Skill getSkill(String id) {
        if (id == null) return null;
        return skills.get(id.toLowerCase());
    }

    public Collection<Skill> getAllSkills() {
        return Collections.unmodifiableCollection(skills.values());
    }

    public List<Skill> getSkillsForClass(RPGClass rpgClass) {
        List<Skill> result = new ArrayList<>();
        for (Skill s : skills.values()) {
            if (s.getRequiredClass() == rpgClass) {
                result.add(s);
            }
        }
        return result;
    }

    public boolean canLearnSkill(PlayerRPGData player, Skill skill) {
        if (player == null || skill == null) return false;
        if (player.getCharacterClass() != skill.getRequiredClass()) return false;
        return player.getLevel() >= skill.getRequiredLevel();
    }

    public boolean learnSkill(PlayerRPGData player, String skillId) {
        Skill skill = getSkill(skillId);
        if (!canLearnSkill(player, skill)) return false;

        Set<String> learned = playerLearnedSkills.computeIfAbsent(player.getPlayerUUID(), k -> new HashSet<>());
        return learned.add(skill.getId());
    }

    public boolean hasLearnedSkill(UUID playerUUID, String skillId) {
        Set<String> learned = playerLearnedSkills.get(playerUUID);
        return learned != null && learned.contains(skillId.toLowerCase());
    }

    public Set<String> getLearnedSkills(UUID playerUUID) {
        return playerLearnedSkills.getOrDefault(playerUUID, Collections.emptySet());
    }

    public void clear() {
        playerLearnedSkills.clear();
    }
}
