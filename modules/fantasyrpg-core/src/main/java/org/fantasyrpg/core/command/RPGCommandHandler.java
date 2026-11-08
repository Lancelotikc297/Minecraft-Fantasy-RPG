package org.fantasyrpg.core.command;

import org.fantasyrpg.core.data.PlayerRPGData;
import org.fantasyrpg.core.data.RPGClass;
import org.fantasyrpg.core.data.RPGDataManager;
import org.fantasyrpg.core.economy.CoinPurse;
import org.fantasyrpg.core.guidebook.GuidebookManager;
import org.fantasyrpg.core.magic.SpellRegistry;
import org.fantasyrpg.core.skill.Skill;
import org.fantasyrpg.core.skill.SkillRegistry;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Обработчик ролевых команд /rpg.
 * Поддерживает:
 * /rpg info
 * /rpg setclass <класс>
 * /rpg addxp <число>
 * /rpg cast <заклинание>
 * /rpg learn <навык>
 * /rpg skills
 * /rpg coin <add/balance> <число>
 * /rpg guidebook <страница>
 */
public class RPGCommandHandler {

    private static final Map<UUID, CoinPurse> playerPurses = new ConcurrentHashMap<>();

    public static CoinPurse getPlayerPurse(UUID playerUUID) {
        return playerPurses.computeIfAbsent(playerUUID, k -> new CoinPurse());
    }

    public static String handleInfo(UUID playerUUID, String playerName) {
        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        if (data == null) {
            return "Игрок не найден!";
        }

        CoinPurse purse = getPlayerPurse(playerUUID);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== Характеристики персонажа: %s ===\n", playerName));
        sb.append(String.format("Класс: %s\n", data.getCharacterClass().getRussianName()));
        sb.append(String.format("Уровень: %d (Опыт: %d/%d)\n", data.getLevel(), data.getCurrentXP(), data.getRequiredXPForNextLevel()));
        sb.append(String.format("Мана: %d/%d\n", data.getCurrentMana(), data.getMaxMana()));
        sb.append(String.format("Атрибуты -> Сила: %d | Ловкость: %d | Интеллект: %d | Выносливость: %d\n",
                data.getStrength(), data.getAgility(), data.getIntelligence(), data.getEndurance()));
        sb.append(String.format("Кошелек: %s", purse.formatRussian()));

        return sb.toString();
    }

    public static String handleSetClass(UUID playerUUID, String className) {
        RPGClass targetClass = RPGClass.fromString(className);
        if (targetClass == RPGClass.NONE && !"none".equalsIgnoreCase(className)) {
            return "Неизвестный класс! Доступные классы: warrior, paladin, mage, necromancer, assassin, archer";
        }

        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        data.setCharacterClass(targetClass);
        return String.format("Класс персонажа изменен на: %s", targetClass.getRussianName());
    }

    public static String handleAddXP(UUID playerUUID, int xp) {
        if (xp <= 0) {
            return "Количество опыта должно быть положительным числом!";
        }

        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        boolean leveledUp = data.addExperience(xp);

        String result = String.format("Добавлено %d очков опыта! Текущий уровень: %d (Опыт: %d/%d)",
                xp, data.getLevel(), data.getCurrentXP(), data.getRequiredXPForNextLevel());

        if (leveledUp) {
            result += String.format("\nПоздравляем! Вы достигли уровня %d!", data.getLevel());
        }

        return result;
    }

    public static String handleCast(UUID playerUUID, String spellId) {
        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        if (data == null) return "Игрок не найден!";

        SpellRegistry.CastResult result = SpellRegistry.getInstance().castSpell(data, spellId, System.currentTimeMillis());
        return result.getMessage();
    }

    public static String handleLearnSkill(UUID playerUUID, String skillId) {
        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        if (data == null) return "Игрок не найден!";

        Skill skill = SkillRegistry.getInstance().getSkill(skillId);
        if (skill == null) {
            return "Неизвестный навык!";
        }

        boolean success = SkillRegistry.getInstance().learnSkill(data, skillId);
        if (success) {
            return String.format("Вы успешно изучили навык: [%s]!", skill.getRussianName());
        } else {
            return String.format("Не удалось изучить навык [%s]. Проверьте соответствие классу и уровню (Требуется: %s, Уровень %d).",
                    skill.getRussianName(), skill.getRequiredClass().getRussianName(), skill.getRequiredLevel());
        }
    }

    public static String handleListSkills(UUID playerUUID) {
        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        if (data == null) return "Игрок не найден!";

        StringBuilder sb = new StringBuilder("=== Доступные навыки для класса ");
        sb.append(data.getCharacterClass().getRussianName()).append(" ===\n");

        for (Skill s : SkillRegistry.getInstance().getSkillsForClass(data.getCharacterClass())) {
            boolean learned = SkillRegistry.getInstance().hasLearnedSkill(playerUUID, s.getId());
            sb.append(String.format("- [%s] %s (Ур. %d): %s\n",
                    learned ? "Изучено" : "Не изучено",
                    s.getRussianName(),
                    s.getRequiredLevel(),
                    s.getDescription()));
        }
        return sb.toString().trim();
    }

    public static String handleCoin(UUID playerUUID, String action, int amount) {
        CoinPurse purse = getPlayerPurse(playerUUID);
        if ("balance".equalsIgnoreCase(action)) {
            return "Ваш баланс: " + purse.formatRussian();
        } else if ("add".equalsIgnoreCase(action)) {
            if (amount <= 0) return "Сумма монет должна быть больше 0!";
            purse.addCopper(amount);
            return String.format("Добавлено %d медных монет! Новый баланс: %s", amount, purse.formatRussian());
        }
        return "Неверное действие с монетами! Используйте /rpg coin balance или /rpg coin add <число>";
    }

    public static String handleGuidebook(String pageId) {
        if (pageId == null || pageId.isEmpty()) {
            pageId = "welcome";
        }
        GuidebookManager.GuidebookPage page = GuidebookManager.getInstance().getPage(pageId);
        if (page == null) {
            return "Страница книги знаний не найдена! Доступные разделы: welcome, classes, magic, dragons, economy";
        }
        return String.format("=== %s ===\n%s", page.getTitle(), page.getContent());
    }
}
