package org.fantasyrpg.dragon;

import org.fantasyrpg.dragon.entity.AmphithereData;
import org.fantasyrpg.dragon.entity.DragonEntityData;
import org.fantasyrpg.dragon.entity.DragonSpecies;
import org.fantasyrpg.dragon.protection.DragonProtectionEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DragonSystemTest {

    private DragonProtectionEngine protectionEngine;

    @BeforeEach
    public void setUp() {
        protectionEngine = DragonProtectionEngine.getInstance();
        protectionEngine.setProtectPlayerBuilds(true);
        protectionEngine.setDestructionMode(DragonProtectionEngine.DestructionMode.LIMITED);
    }

    @Test
    public void testPlayerBuildsProtectedByDefault() {
        // Player build should NEVER be destroyed by dragon by default
        boolean canDestroy = protectionEngine.canDragonDestroyBlock(10, 20, true, false);
        assertFalse(canDestroy, "Player-built structures must be protected by default (Section 6.2)!");

        // Protected settlement chunk
        protectionEngine.addProtectedChunk(5, 5);
        boolean canDestroyZone = protectionEngine.canDragonDestroyBlock(5, 5, false, true);
        assertFalse(canDestroyZone, "Protected chunk must protect natural and placed blocks");
    }

    @Test
    public void testDragonGrowthStagesAndHealth() {
        DragonEntityData dragon = new DragonEntityData(UUID.randomUUID(), DragonSpecies.FIRE);
        assertEquals(1, dragon.getStage());
        assertEquals(50, dragon.getMaxHealth());

        dragon.setStage(3);
        assertEquals(3, dragon.getStage());
        assertEquals(250, dragon.getMaxHealth());

        dragon.setStage(5);
        assertEquals(5, dragon.getStage());
        assertEquals(600, dragon.getMaxHealth());
    }

    @Test
    public void testDragonTamingAndCommands() {
        UUID owner = UUID.randomUUID();
        DragonEntityData dragon = new DragonEntityData(UUID.randomUUID(), DragonSpecies.ICE);
        assertFalse(dragon.isTamed());

        dragon.tame(owner);
        assertTrue(dragon.isTamed());
        assertEquals(owner, dragon.getOwnerUUID());
        assertEquals(DragonEntityData.DragonCommand.FOLLOW, dragon.getCommand());

        dragon.setCommand(DragonEntityData.DragonCommand.GUARD);
        assertEquals(DragonEntityData.DragonCommand.GUARD, dragon.getCommand());

        String status = dragon.renderStatusPanel();
        assertTrue(status.contains("Ледяной дракон"));
        assertTrue(status.contains("Охранять территорию"));
    }

    @Test
    public void testAmphithereStats() {
        AmphithereData amphithere = new AmphithereData(UUID.randomUUID());
        assertTrue(amphithere.getFlightSpeed() > 1.2, "Amphitheres should be faster than dragons");
        assertFalse(amphithere.hasSaddle());

        amphithere.setHasSaddle(true);
        assertTrue(amphithere.hasSaddle());
        assertTrue(amphithere.renderStatus().contains("Амфиптерий"));
    }
}
