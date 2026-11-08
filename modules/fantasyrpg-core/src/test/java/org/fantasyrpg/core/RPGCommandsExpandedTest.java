package org.fantasyrpg.core;

import org.fantasyrpg.core.command.RPGCommandHandler;
import org.fantasyrpg.core.data.PlayerRPGData;
import org.fantasyrpg.core.data.RPGClass;
import org.fantasyrpg.core.data.RPGDataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RPGCommandsExpandedTest {

    private UUID playerUUID;

    @BeforeEach
    public void setUp() {
        RPGDataManager.getInstance().clear();
        playerUUID = UUID.randomUUID();
    }

    @Test
    public void testCommandCastSpell() {
        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        data.setCharacterClass(RPGClass.MAGE);

        String result = RPGCommandHandler.handleCast(playerUUID, "fireball");
        assertTrue(result.contains("Сотворено заклинание"), "Expected successful cast output: " + result);

        String invalid = RPGCommandHandler.handleCast(playerUUID, "unknown_spell");
        assertTrue(invalid.contains("Неизвестное заклинание"));
    }

    @Test
    public void testCommandLearnSkill() {
        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        data.setCharacterClass(RPGClass.WARRIOR);

        String result = RPGCommandHandler.handleLearnSkill(playerUUID, "shield_bash");
        assertTrue(result.contains("Вы успешно изучили навык: [Удар щитом]!"));

        String list = RPGCommandHandler.handleListSkills(playerUUID);
        assertTrue(list.contains("Изучено"));
    }

    @Test
    public void testCommandCoinManagement() {
        String balInit = RPGCommandHandler.handleCoin(playerUUID, "balance", 0);
        assertTrue(balInit.contains("0 золотых, 0 серебряных, 0 медных"));

        String addResult = RPGCommandHandler.handleCoin(playerUUID, "add", 1250);
        assertTrue(addResult.contains("Добавлено 1250 медных монет"));

        String balAfter = RPGCommandHandler.handleCoin(playerUUID, "balance", 0);
        assertTrue(balAfter.contains("12 серебряных, 50 медных"));
    }

    @Test
    public void testCommandGuidebook() {
        String welcome = RPGCommandHandler.handleGuidebook("welcome");
        assertTrue(welcome.contains("Введение в мир"));

        String dragons = RPGCommandHandler.handleGuidebook("dragons");
        assertTrue(dragons.contains("защищены"));

        String invalid = RPGCommandHandler.handleGuidebook("invalid_page");
        assertTrue(invalid.contains("не найдена"));
    }
}
