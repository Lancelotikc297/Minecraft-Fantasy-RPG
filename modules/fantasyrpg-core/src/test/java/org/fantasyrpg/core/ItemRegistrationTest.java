package org.fantasyrpg.core;

import org.fantasyrpg.core.item.ItemRarity;
import org.fantasyrpg.core.item.RPGItemDefinition;
import org.fantasyrpg.core.item.RPGItemRegistry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemRegistrationTest {

    @Test
    public void testAllItemsRegistered() {
        RPGItemRegistry registry = RPGItemRegistry.getInstance();
        assertNotNull(registry.getItem("copper_coin"));
        assertNotNull(registry.getItem("silver_coin"));
        assertNotNull(registry.getItem("gold_coin"));
        assertNotNull(registry.getItem("flame_blade"));
        assertNotNull(registry.getItem("frost_dagger"));
        assertNotNull(registry.getItem("thunder_mace"));
        assertNotNull(registry.getItem("shadow_scythe"));
        assertNotNull(registry.getItem("holy_warhammer"));
        assertNotNull(registry.getItem("ring_of_vitality"));
        assertNotNull(registry.getItem("amulet_of_wisdom"));
        assertNotNull(registry.getItem("cloak_of_shadows"));
        assertNotNull(registry.getItem("guidebook_item"));
    }

    @Test
    public void testWeaponAttributes() {
        RPGItemDefinition flameBlade = RPGItemRegistry.getInstance().getItem("flame_blade");
        assertTrue(flameBlade.isWeapon());
        assertEquals(14, flameBlade.getBaseDamage());
        assertEquals(ItemRarity.EPIC, flameBlade.getRarity());
        assertEquals(27, flameBlade.getEffectiveDamage());
        assertEquals("fire", flameBlade.getElementalType());
    }

    @Test
    public void testCoinStacking() {
        RPGItemDefinition copper = RPGItemRegistry.getInstance().getItem("copper_coin");
        assertEquals(64, copper.getMaxStackSize());
        assertFalse(copper.isWeapon());
    }
}
