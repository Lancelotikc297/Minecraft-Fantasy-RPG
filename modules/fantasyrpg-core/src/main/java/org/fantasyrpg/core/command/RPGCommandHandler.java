package org.fantasyrpg.core.command;

import org.fantasyrpg.core.data.PlayerRPGData;
import org.fantasyrpg.core.data.RPGClass;
import org.fantasyrpg.core.data.RPGDataManager;

import java.util.UUID;

/**
 * Обработчик ролевых команд /rpg.
 * Поддерживает осмотр профиля, установку класса, добавление опыта и вывод статов на русском языке.
 */
public class RPGCommandHandler {

    public static String handleInfo(UUID playerUUID, String playerName) {
        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        if (data == null) {
            return "Игрок не найден!";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== Характеристики персонажа: %s ===\n", playerName));
        sb.append(String.format("Класс: %s\n", data.getCharacterClass().getRussianName()));
        sb.append(String.format("Уровень: %d (Опыт: %d/%d)\n", data.getLevel(), data.getCurrentXP(), data.getRequiredXPForNextLevel()));
        sb.append(String.format("Мана: %d/%d\n", data.getCurrentMana(), data.getMaxMana()));
        sb.append(String.format("Атрибуты -> Сила: %d | Ловкость: %d | Интеллект: %d | Выносливость: %d",
                data.getStrength(), data.getAgility(), data.getIntelligence(), data.getEndurance()));

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
}
