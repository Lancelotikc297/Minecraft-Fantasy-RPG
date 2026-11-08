package org.fantasyrpg.core;

import org.fantasyrpg.core.data.PlayerRPGData;
import org.fantasyrpg.core.data.RPGClass;
import org.fantasyrpg.core.magic.Spell;
import org.fantasyrpg.core.magic.SpellRegistry;
import org.fantasyrpg.core.magic.SpellSchool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SpellRegistryTest {

    private SpellRegistry registry;
    private PlayerRPGData caster;

    @BeforeEach
    public void setUp() {
        registry = SpellRegistry.getInstance();
        registry.clearCooldowns();
        caster = new PlayerRPGData(UUID.randomUUID());
    }

    @Test
    public void testForbiddenSchoolsCannotBeRegistered() {
        Spell illusionSpell = new Spell("mirror_image", "Зеркальный образ", "Создает иллюзорного двойника",
                SpellSchool.ILLUSION, 20, 10, 0, 10);
        assertFalse(registry.register(illusionSpell), "Illusion spells must be forbidden per Section 4");
        assertNull(registry.getSpell("mirror_image"));

        Spell portalSpell = new Spell("teleport_gate", "Врата телепортации", "Открывает портал",
                SpellSchool.SPATIAL_PORTAL, 50, 60, 0, 15);
        assertFalse(registry.register(portalSpell), "Spatial portal spells must be forbidden per Section 4");
        assertNull(registry.getSpell("teleport_gate"));
    }

    @Test
    public void testCastSpellSuccessAndManaDeduction() {
        caster.setCharacterClass(RPGClass.MAGE); // Intelligence = 16, Mana = 94
        long time = 100000L;

        SpellRegistry.CastResult result = registry.castSpell(caster, "fireball", time);
        assertTrue(result.isSuccess());
        assertTrue(result.getDamage() > 25, "Damage should scale with high Mage intelligence");
        assertEquals(74, caster.getCurrentMana(), "20 mana should be deducted from 94");
    }

    @Test
    public void testCastFailsWhenOutOfMana() {
        caster.setCharacterClass(RPGClass.WARRIOR); // Low intelligence
        caster.setCurrentMana(5);
        long time = 100000L;

        SpellRegistry.CastResult result = registry.castSpell(caster, "fireball", time);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Недостаточно"));
    }

    @Test
    public void testCastFailsDuringCooldown() {
        caster.setCharacterClass(RPGClass.MAGE);
        long time = 100000L;

        SpellRegistry.CastResult first = registry.castSpell(caster, "fireball", time);
        assertTrue(first.isSuccess());

        // Cast immediately again (cooldown is 3 sec)
        SpellRegistry.CastResult second = registry.castSpell(caster, "fireball", time + 1000L);
        assertFalse(second.isSuccess());
        assertTrue(second.getMessage().contains("перезаряжается"));

        // Cast after 4 seconds (after cooldown)
        SpellRegistry.CastResult third = registry.castSpell(caster, "fireball", time + 4000L);
        assertTrue(third.isSuccess());
    }
}
