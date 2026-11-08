package org.fantasyrpg.core;

import org.fantasyrpg.core.config.RPGConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RPGConfigTest {

    @Test
    public void testPlayerBuildsProtectedByDefault() {
        RPGConfig config = new RPGConfig();
        assertTrue(config.isProtectPlayerBuilds(), "Player builds must be protected by default according to Section 6.2!");
    }

    @Test
    public void testDefaultLanguageIsRussian() {
        RPGConfig config = new RPGConfig();
        assertEquals("ru_ru", config.getLanguage(), "Default language must be ru_ru");
    }

    @Test
    public void testForbiddenMagicSchoolsDisallowed() {
        RPGConfig config = new RPGConfig();
        assertFalse(config.isSchoolAllowed("illusion"), "Illusion school must be forbidden per Section 4");
        assertFalse(config.isSchoolAllowed("spatial_teleport"), "Spatial teleport must be forbidden per Section 4");
        assertTrue(config.isSchoolAllowed("fire"), "Fire magic should be allowed");
        assertTrue(config.isSchoolAllowed("ice"), "Ice magic should be allowed");
        assertTrue(config.isSchoolAllowed("lightning"), "Lightning magic should be allowed");
        assertTrue(config.isSchoolAllowed("dark"), "Dark magic should be allowed");
    }
}
