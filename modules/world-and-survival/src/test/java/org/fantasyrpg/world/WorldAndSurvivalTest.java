package org.fantasyrpg.world;

import org.fantasyrpg.world.backpack.BackpackSystem;
import org.fantasyrpg.world.boss.BossEncounterEngine;
import org.fantasyrpg.world.map.MapNavigationBridge;
import org.fantasyrpg.world.season.SeasonManager;
import org.fantasyrpg.world.tree.TreeFellingEngine;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class WorldAndSurvivalTest {

    @Test
    public void testTreeFellingMechanic() {
        TreeFellingEngine engine = TreeFellingEngine.getInstance();
        engine.setEnabled(true);

        // Fell tree of 8 logs with 50 durability axe
        TreeFellingEngine.FellingResult result = engine.fellTree(8, 50);
        assertTrue(result.isSuccess());
        assertEquals(8, result.getLogsHarvested());
        assertEquals(8, result.getDurabilityConsumed(), "Durability must be deducted for every log felled");
        assertTrue(result.getSticksDropped() > 0);
        assertTrue(result.getSaplingsDropped() > 0);

        // Limited axe durability (only 4 durability left)
        TreeFellingEngine.FellingResult brokenAxeResult = engine.fellTree(10, 4);
        assertEquals(4, brokenAxeResult.getLogsHarvested());
        assertEquals(4, brokenAxeResult.getDurabilityConsumed());
    }

    @Test
    public void testMultiPhaseBossEncounter() {
        BossEncounterEngine boss = new BossEncounterEngine("nether_behemoth", "Исполин Бездны", 1000);
        assertEquals(BossEncounterEngine.BossPhase.PHASE_1_NORMAL, boss.getCurrentPhase());

        // Deal 500 damage (50% HP left -> Phase 2 Enraged)
        boss.applyDamage(500);
        assertEquals(BossEncounterEngine.BossPhase.PHASE_2_ENRAGED, boss.getCurrentPhase());

        // Deal 300 more damage (20% HP left -> Phase 3 Desperation)
        boss.applyDamage(300);
        assertEquals(BossEncounterEngine.BossPhase.PHASE_3_DESPERATION, boss.getCurrentPhase());

        // Defeat boss
        boss.applyDamage(200);
        assertTrue(boss.isDefeated());
    }

    @Test
    public void testSeasonsCycling() {
        SeasonManager sm = SeasonManager.getInstance();
        assertEquals(SeasonManager.Season.SPRING, sm.getCurrentSeason());

        // Advance 7 days
        for (int i = 0; i < 7; i++) {
            sm.advanceDay();
        }
        assertEquals(SeasonManager.Season.SUMMER, sm.getCurrentSeason());
    }

    @Test
    public void testBackpackCapacityAndUpgrades() {
        BackpackSystem netheriteBackpack = new BackpackSystem(BackpackSystem.BackpackTier.NETHERITE);
        assertEquals(120, netheriteBackpack.getTier().getSlots());
        assertEquals(16384, netheriteBackpack.getTier().getMaxStackMultiplier());

        assertTrue(netheriteBackpack.addItem("minecraft:iron_ore", 5000));
        assertEquals(5000, netheriteBackpack.getItemCount("minecraft:iron_ore"));

        netheriteBackpack.setCraftingModuleInstalled(true);
        assertTrue(netheriteBackpack.isCraftingModuleInstalled());
    }

    @Test
    public void testMapNavigationWaypoints() {
        UUID playerUUID = UUID.randomUUID();
        MapNavigationBridge map = MapNavigationBridge.getInstance();

        map.addWaypoint(playerUUID, "Крепость", 100, 70, -250, "minecraft:overworld");
        assertEquals(1, map.getWaypoints(playerUUID).size());
        assertEquals("Крепость", map.getWaypoints(playerUUID).get(0).getName());
    }
}
