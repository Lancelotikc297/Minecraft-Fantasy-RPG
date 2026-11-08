package org.fantasyrpg.core.gui;

import org.fantasyrpg.core.command.RPGCommandHandler;
import org.fantasyrpg.core.data.PlayerRPGData;
import org.fantasyrpg.core.data.RPGDataManager;
import org.fantasyrpg.core.economy.CoinPurse;
import org.fantasyrpg.core.guidebook.GuidebookManager;

import java.util.UUID;

/**
 * Внутриигровой интерфейс персонажа и интерактивная книга знаний.
 */
public class RPGCharacterGUI {

    public static String renderCharacterScreen(UUID playerUUID, String playerName) {
        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        CoinPurse purse = RPGCommandHandler.getPlayerPurse(playerUUID);

        int xpProgress = (int) (((double) data.getCurrentXP() / Math.max(1, data.getRequiredXPForNextLevel())) * 10);
        String progressBar = "■".repeat(Math.min(10, Math.max(0, xpProgress))) + "□".repeat(Math.max(0, 10 - xpProgress));

        return """
                ┌────────────────────────────────────────────────────────┐
                │             ХАРАКТЕРИСТИКИ ПЕРСОНАЖА                   │
                ├────────────────────────────────────────────────────────┤
                │ Имя: %-25s Класс: %-17s│
                │ Уровень: %-4d  Прогресс XP: [%s] (%d/%d)      │
                │ Мана: %d/%-4d  Бонус здоровья: +%-3d                   │
                ├────────────────────────────────────────────────────────┤
                │ АТРИБУТЫ (Свободных очков для распределения: %-3d):    │
                │  - [1] Сила:         %-3d (Физический урон)            │
                │  - [2] Ловкость:     %-3d (Шанс крита, скорость)       │
                │  - [3] Интеллект:    %-3d (Сила магии, запас маны)     │
                │  - [4] Выносливость: %-3d (Здоровье, сопротивление)    │
                ├────────────────────────────────────────────────────────┤
                │ КОШЕЛЕК: %-45s │
                └────────────────────────────────────────────────────────┘
                Команда распределения: /rpg allocate <атрибут> <количество>
                """
                .formatted(
                        playerName,
                        data.getCharacterClass().getRussianName(),
                        data.getLevel(),
                        progressBar,
                        data.getCurrentXP(),
                        data.getRequiredXPForNextLevel(),
                        data.getCurrentMana(),
                        data.getMaxMana(),
                        data.getMaxHealthBonus(),
                        data.getAvailableAttributePoints(),
                        data.getStrength(),
                        data.getAgility(),
                        data.getIntelligence(),
                        data.getEndurance(),
                        purse.formatRussian()
                );
    }

    public static String renderGuidebookIndex() {
        StringBuilder sb = new StringBuilder("=== Книга знаний RPG: Содержание ===\n");
        for (GuidebookManager.GuidebookPage p : GuidebookManager.getInstance().getAllPages()) {
            sb.append(String.format(" - [%s] %s\n", p.getId(), p.getTitle()));
        }
        sb.append("Открыть раздел: /rpg guide <id>");
        return sb.toString();
    }
}
