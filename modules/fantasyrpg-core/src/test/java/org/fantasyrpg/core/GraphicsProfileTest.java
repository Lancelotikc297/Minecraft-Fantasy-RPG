package org.fantasyrpg.core;

import org.fantasyrpg.core.graphics.GraphicsProfileManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphicsProfileTest {

    @Test
    public void testGraphicsProfilesConfigurations() {
        GraphicsProfileManager manager = GraphicsProfileManager.getInstance();

        // 1. Laptop profile
        manager.setProfile(GraphicsProfileManager.Profile.LAPTOP);
        assertEquals(GraphicsProfileManager.Profile.LAPTOP, manager.getActiveProfile());
        assertEquals(8, manager.getActiveProfile().getRenderDistanceChunks());
        assertTrue(manager.getActiveProfile().isAggressiveEntityCulling());

        // 2. Cinematic profile
        manager.setProfile(GraphicsProfileManager.Profile.CINEMATIC);
        assertEquals(18, manager.getActiveProfile().getRenderDistanceChunks());
        assertTrue(manager.getActiveProfile().isDynamicLighting());

        // 3. Status string
        String status = manager.renderStatus();
        assertTrue(status.contains("Кинематографичный"));
    }
}
