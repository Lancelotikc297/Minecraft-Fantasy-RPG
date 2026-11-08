package org.fantasyrpg.core;

import org.fantasyrpg.core.config.RPGConfigCenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigCenterTest {

    private RPGConfigCenter configCenter;

    @BeforeEach
    public void setUp() {
        configCenter = RPGConfigCenter.getInstance();
    }

    @Test
    public void testCannotDisableDragonBuildProtection() {
        boolean updated = configCenter.updateSetting("dragons.protect_player_builds", false);
        assertFalse(updated, "Must not allow disabling player build protection");
        assertEquals(true, configCenter.getSetting("dragons.protect_player_builds"));
    }

    @Test
    public void testCannotEnableForbiddenMagicSchools() {
        boolean updatedIllusion = configCenter.updateSetting("magic.school_illusion", true);
        assertFalse(updatedIllusion, "Must not allow enabling illusion magic");

        boolean updatedSpatial = configCenter.updateSetting("magic.school_spatial", true);
        assertFalse(updatedSpatial, "Must not allow enabling spatial portal magic");
    }

    @Test
    public void testUpdateGraphicsProfilesPhaseG() {
        assertTrue(configCenter.updateSetting("graphics.profile", "low_laptop"));
        assertEquals("low_laptop", configCenter.getSetting("graphics.profile"));

        assertTrue(configCenter.updateSetting("graphics.profile", "cinematic"));
        assertEquals("cinematic", configCenter.getSetting("graphics.profile"));

        assertFalse(configCenter.updateSetting("graphics.profile", "ultra_extreme_invalid"));
    }

    @Test
    public void testRenderCategoryMenu() {
        String menu = configCenter.renderCategoryMenu(RPGConfigCenter.ConfigCategory.DRAGONS_AND_BUILDINGS);
        assertNotNull(menu);
        assertTrue(menu.contains("Драконы и защита построек"));
        assertTrue(menu.contains("protect_player_builds"));
    }
}
