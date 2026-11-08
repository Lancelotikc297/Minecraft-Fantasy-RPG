package org.fantasyrpg.core;

import org.fantasyrpg.core.combat.CombatEngine;
import org.fantasyrpg.core.data.PlayerRPGData;
import org.fantasyrpg.core.data.RPGClass;
import org.fantasyrpg.core.economy.CoinPurse;
import org.fantasyrpg.core.guidebook.GuidebookManager;
import org.fantasyrpg.core.item.ItemRarity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SystemsTest {

    @Test
    public void testCombatPhysicalAttackCalculations() {
        PlayerRPGData attacker = new PlayerRPGData(UUID.randomUUID());
        attacker.setCharacterClass(RPGClass.WARRIOR); // Strength = 14

        // 1. Regular strike
        CombatEngine.AttackResult result = CombatEngine.calculatePhysicalAttack(
                attacker, 10, ItemRarity.COMMON, 0, false, 0.99);
        assertTrue(result.getDamage() > 10, "Strength bonus should increase weapon damage");
        assertFalse(result.isCritical());
        assertFalse(result.isBlocked());

        // 2. Legendary weapon critical strike
        CombatEngine.AttackResult critResult = CombatEngine.calculatePhysicalAttack(
                attacker, 10, ItemRarity.LEGENDARY, 0, false, 0.01);
        assertTrue(critResult.isCritical());
        assertTrue(critResult.getDamage() > result.getDamage() * 2);

        // 3. Target blocking with armor
        CombatEngine.AttackResult blockResult = CombatEngine.calculatePhysicalAttack(
                attacker, 10, ItemRarity.COMMON, 20, true, 0.99);
        assertTrue(blockResult.isBlocked());
        assertTrue(blockResult.getDamage() < result.getDamage());
    }

    @Test
    public void testCoinPurseConversions() {
        CoinPurse purse = new CoinPurse();
        purse.addGold(2);   // 20000 copper
        purse.addSilver(15); // 1500 copper
        purse.addCopper(45); // 45 copper

        assertEquals(2, purse.getGoldCoins());
        assertEquals(15, purse.getSilverCoins());
        assertEquals(45, purse.getCopperCoins());
        assertEquals(21545L, purse.getTotalCopperUnits());

        // Withdraw 545 copper
        boolean success = purse.withdrawCopper(545);
        assertTrue(success);
        assertEquals(2, purse.getGoldCoins());
        assertEquals(10, purse.getSilverCoins());
        assertEquals(0, purse.getCopperCoins());

        // Overdraft fails
        assertFalse(purse.withdrawCopper(9999999L));
    }

    @Test
    public void testGuidebookContainsEssentialRussianPages() {
        GuidebookManager guide = GuidebookManager.getInstance();
        assertNotNull(guide.getPage("welcome"));
        assertNotNull(guide.getPage("classes"));
        assertNotNull(guide.getPage("magic"));
        assertNotNull(guide.getPage("dragons"));
        assertNotNull(guide.getPage("economy"));

        assertTrue(guide.getPage("dragons").getContent().contains("защищены"));
        assertTrue(guide.getPage("magic").getContent().contains("запрещены"));
    }
}
