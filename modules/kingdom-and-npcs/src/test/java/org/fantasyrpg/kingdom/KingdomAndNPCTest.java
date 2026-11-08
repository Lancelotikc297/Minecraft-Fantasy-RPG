package org.fantasyrpg.kingdom;

import org.fantasyrpg.kingdom.faction.FactionManager;
import org.fantasyrpg.kingdom.npc.NPCData;
import org.fantasyrpg.kingdom.quest.QuestManager;
import org.fantasyrpg.kingdom.settlement.KingdomSettlementManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class KingdomAndNPCTest {

    private UUID playerUUID;

    @BeforeEach
    public void setUp() {
        playerUUID = UUID.randomUUID();
        QuestManager.getInstance().clear();
        KingdomSettlementManager.getInstance().clear();
    }

    @Test
    public void testNPCInteractionAndGreeting() {
        NPCData blacksmith = new NPCData(UUID.randomUUID(), "Торин", NPCData.Profession.BLACKSMITH);
        assertEquals(NPCData.Profession.BLACKSMITH, blacksmith.getProfession());
        assertEquals(0, blacksmith.getRelationshipPoints());

        blacksmith.adjustRelationship(15);
        assertEquals(15, blacksmith.getRelationshipPoints());

        String dialogue = blacksmith.interact();
        assertTrue(dialogue.contains("Кузнец"));
        assertTrue(dialogue.contains("Торин"));
        assertTrue(dialogue.contains("починить драконью броню"));
    }

    @Test
    public void testFactionReputationProgression() {
        FactionManager factions = FactionManager.getInstance();

        assertEquals(FactionManager.ReputationLevel.NEUTRAL, factions.getReputationLevel(playerUUID, "paladins"));

        factions.adjustReputation(playerUUID, "paladins", 600);
        assertEquals(FactionManager.ReputationLevel.FRIENDLY, factions.getReputationLevel(playerUUID, "paladins"));

        factions.adjustReputation(playerUUID, "paladins", 1000);
        assertEquals(FactionManager.ReputationLevel.HONORED, factions.getReputationLevel(playerUUID, "paladins"));
    }

    @Test
    public void testQuestCompletion() {
        QuestManager qm = QuestManager.getInstance();
        assertNotNull(qm.getQuest("starter_slayer"));

        assertFalse(qm.isQuestCompleted(playerUUID, "starter_slayer"));
        boolean completed = qm.completeQuest(playerUUID, "starter_slayer");
        assertTrue(completed);
        assertTrue(qm.isQuestCompleted(playerUUID, "starter_slayer"));
    }

    @Test
    public void testKingdomSettlementCreation() {
        KingdomSettlementManager km = KingdomSettlementManager.getInstance();
        KingdomSettlementManager.Settlement settlement = km.createSettlement(playerUUID, "Новый Вайтран");

        assertNotNull(settlement);
        assertEquals("Новый Вайтран", settlement.getName());
        assertEquals(1, settlement.getLevel());

        settlement.addResident();
        assertEquals(1, settlement.getPopulation());

        settlement.constructBlueprint("Ратуша");
        settlement.constructBlueprint("Казармы стражи");
        settlement.constructBlueprint("Кузница");

        assertEquals(2, settlement.getLevel(), "Settlement should upgrade to Level 2 after 3 blueprints");
    }
}
