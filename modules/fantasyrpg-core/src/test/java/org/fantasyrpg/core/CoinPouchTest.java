package org.fantasyrpg.core;

import org.fantasyrpg.core.economy.CoinPouch;
import org.fantasyrpg.core.economy.CoinPouchManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CoinPouchTest {

    private UUID playerUUID;
    private CoinPouchManager manager;

    @BeforeEach
    public void setUp() {
        manager = CoinPouchManager.getInstance();
        manager.clear();
        playerUUID = UUID.randomUUID();
    }

    @Test
    public void testStarterPouchProvision() {
        boolean given = manager.provideStarterPouchAndGuide(playerUUID);
        assertTrue(given, "Starter pouch must be provided at the beginning of a new world");

        CoinPouch pouch = manager.getOrCreatePouch(playerUUID);
        assertEquals(50, pouch.getTotalCopperValue());
        assertEquals(50, pouch.getCopper());
    }

    @Test
    public void testAutoConversionWithoutValueLoss() {
        CoinPouch pouch = manager.getOrCreatePouch(playerUUID);

        // Add 150 copper -> 1 silver, 50 copper
        pouch.depositCopper(150);
        assertEquals(1, pouch.getSilver());
        assertEquals(50, pouch.getCopper());
        assertEquals(0, pouch.getGold());
        assertEquals(150, pouch.getTotalCopperValue());

        // Add 9900 copper -> total 10050 -> 1 gold, 0 silver, 50 copper
        pouch.depositCopper(9900);
        assertEquals(1, pouch.getGold());
        assertEquals(0, pouch.getSilver());
        assertEquals(50, pouch.getCopper());
        assertEquals(10050, pouch.getTotalCopperValue());
    }

    @Test
    public void testPouchPersistsAcrossDeath() {
        CoinPouch pouch = manager.getOrCreatePouch(playerUUID);
        pouch.deposit(2, 5, 20); // 20520 copper

        manager.onPlayerDeath(playerUUID);

        CoinPouch respawnPouch = manager.getOrCreatePouch(playerUUID);
        assertEquals(2, respawnPouch.getGold());
        assertEquals(5, respawnPouch.getSilver());
        assertEquals(20, respawnPouch.getCopper());
    }
}
