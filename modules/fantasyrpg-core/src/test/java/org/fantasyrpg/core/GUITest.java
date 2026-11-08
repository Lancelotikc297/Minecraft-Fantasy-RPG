package org.fantasyrpg.core;

import org.fantasyrpg.core.data.PlayerRPGData;
import org.fantasyrpg.core.data.RPGClass;
import org.fantasyrpg.core.data.RPGDataManager;
import org.fantasyrpg.core.gui.RPGCharacterGUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GUITest {

    private UUID playerUUID;

    @BeforeEach
    public void setUp() {
        RPGDataManager.getInstance().clear();
        playerUUID = UUID.randomUUID();
    }

    @Test
    public void testCharacterScreenRendering() {
        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        data.setCharacterClass(RPGClass.PALADIN);
        data.addExperience(100);

        String screen = RPGCharacterGUI.renderCharacterScreen(playerUUID, "PaladinArthas");
        assertNotNull(screen);
        assertTrue(screen.contains("PaladinArthas"));
        assertTrue(screen.contains("Паладин"));
        assertTrue(screen.contains("ХАРАКТЕРИСТИКИ ПЕРСОНАЖА"));
    }

    @Test
    public void testGuidebookIndexRendering() {
        String index = RPGCharacterGUI.renderGuidebookIndex();
        assertNotNull(index);
        assertTrue(index.contains("welcome"));
        assertTrue(index.contains("dragons"));
        assertTrue(index.contains("magic"));
    }
}
