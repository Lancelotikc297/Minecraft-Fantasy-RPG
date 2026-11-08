package org.fantasyrpg.core;

import net.minecraftforge.fml.common.Mod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ForgeOfficialModTest {

    @Test
    public void testOfficialForgeModAnnotation() {
        Mod mod = FantasyRPGCore.class.getAnnotation(Mod.class);
        assertNotNull(mod, "FantasyRPGCore must be annotated with official net.minecraftforge.fml.common.Mod");
        assertEquals("fantasyrpg_core", mod.value());
    }
}
