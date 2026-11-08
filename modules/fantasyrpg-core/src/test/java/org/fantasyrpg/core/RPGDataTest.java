package org.fantasyrpg.core;

import org.fantasyrpg.core.command.RPGCommandHandler;
import org.fantasyrpg.core.data.PlayerRPGData;
import org.fantasyrpg.core.data.RPGClass;
import org.fantasyrpg.core.data.RPGDataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RPGDataTest {

    private UUID playerUUID;

    @BeforeEach
    public void setUp() {
        RPGDataManager.getInstance().clear();
        playerUUID = UUID.randomUUID();
    }

    @Test
    public void testInitialPlayerStats() {
        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        assertNotNull(data);
        assertEquals(1, data.getLevel());
        assertEquals(0, data.getCurrentXP());
        assertEquals(RPGClass.NONE, data.getCharacterClass());
        assertEquals(10, data.getStrength());
        assertEquals(10, data.getAgility());
        assertEquals(10, data.getIntelligence());
        assertEquals(10, data.getEndurance());
        assertEquals(70, data.getMaxMana()); // 30 + 10*4
    }

    @Test
    public void testClassAssignmentModifiesStats() {
        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);

        // Assign Mage
        data.setCharacterClass(RPGClass.MAGE);
        assertEquals(RPGClass.MAGE, data.getCharacterClass());
        assertEquals(16, data.getIntelligence());
        assertEquals(7, data.getStrength());
        assertEquals(94, data.getMaxMana()); // 30 + 16*4

        // Assign Warrior
        data.setCharacterClass(RPGClass.WARRIOR);
        assertEquals(RPGClass.WARRIOR, data.getCharacterClass());
        assertEquals(14, data.getStrength());
        assertEquals(13, data.getEndurance());
    }

    @Test
    public void testExperienceAndLevelUp() {
        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        int needed = data.getRequiredXPForNextLevel(); // 100 for lvl 1

        boolean leveledUp = data.addExperience(50);
        assertFalse(leveledUp);
        assertEquals(1, data.getLevel());
        assertEquals(50, data.getCurrentXP());

        leveledUp = data.addExperience(60); // Total 110 -> lvl 2, 10 XP remainder
        assertTrue(leveledUp);
        assertEquals(2, data.getLevel());
        assertEquals(10, data.getCurrentXP());
        assertEquals(11, data.getStrength());
    }

    @Test
    public void testCommandHandlerInfo() {
        String info = RPGCommandHandler.handleInfo(playerUUID, "Steve");
        assertTrue(info.contains("Steve"));
        assertTrue(info.contains("Уровень: 1"));
        assertTrue(info.contains("Сила: 10"));
    }

    @Test
    public void testCommandHandlerSetClass() {
        String response = RPGCommandHandler.handleSetClass(playerUUID, "paladin");
        assertTrue(response.contains("Паладин"));

        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        assertEquals(RPGClass.PALADIN, data.getCharacterClass());

        String invalid = RPGCommandHandler.handleSetClass(playerUUID, "unknown_class");
        assertTrue(invalid.contains("Неизвестный класс"));
    }

    @Test
    public void testCommandHandlerAddXP() {
        String response = RPGCommandHandler.handleAddXP(playerUUID, 150);
        assertTrue(response.contains("Добавлено 150 очков опыта"));
        assertTrue(response.contains("Вы достигли уровня 2"));
    }
}
