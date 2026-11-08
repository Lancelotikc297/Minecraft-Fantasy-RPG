package org.fantasyrpg.core;

import org.fantasyrpg.core.data.PlayerRPGData;
import org.fantasyrpg.core.data.RPGClass;
import org.fantasyrpg.core.skill.Skill;
import org.fantasyrpg.core.skill.SkillRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SkillRegistryTest {

    private SkillRegistry registry;
    private PlayerRPGData player;

    @BeforeEach
    public void setUp() {
        registry = SkillRegistry.getInstance();
        registry.clear();
        player = new PlayerRPGData(UUID.randomUUID());
    }

    @Test
    public void testDefaultSkillsLoadedForAllClasses() {
        for (RPGClass c : RPGClass.values()) {
            if (c == RPGClass.NONE) continue;
            List<Skill> skills = registry.getSkillsForClass(c);
            assertEquals(3, skills.size(), "Each class should have 3 core abilities registered");
        }
    }

    @Test
    public void testCannotLearnSkillWithWrongClass() {
        player.setCharacterClass(RPGClass.WARRIOR);
        player.addExperience(5000); // High level

        // Try learning Mage skill
        boolean learned = registry.learnSkill(player, "meteor_call");
        assertFalse(learned);
        assertFalse(registry.hasLearnedSkill(player.getPlayerUUID(), "meteor_call"));
    }

    @Test
    public void testCannotLearnSkillBelowLevelRequirement() {
        player.setCharacterClass(RPGClass.WARRIOR);
        assertEquals(1, player.getLevel());

        // Level 5 skill
        boolean learned = registry.learnSkill(player, "whirlwind");
        assertFalse(learned);

        // Level 1 skill
        boolean learnedLvl1 = registry.learnSkill(player, "shield_bash");
        assertTrue(learnedLvl1);
        assertTrue(registry.hasLearnedSkill(player.getPlayerUUID(), "shield_bash"));
    }

    @Test
    public void testLearnSkillAfterLevelingUp() {
        player.setCharacterClass(RPGClass.ARCHER);
        player.addExperience(3000); // Level up past level 5
        assertTrue(player.getLevel() >= 5);

        assertTrue(registry.learnSkill(player, "rapid_shot"));
        assertTrue(registry.learnSkill(player, "explosive_arrow"));
        assertTrue(registry.learnSkill(player, "rain_of_arrows"));

        assertEquals(3, registry.getLearnedSkills(player.getPlayerUUID()).size());
    }
}
