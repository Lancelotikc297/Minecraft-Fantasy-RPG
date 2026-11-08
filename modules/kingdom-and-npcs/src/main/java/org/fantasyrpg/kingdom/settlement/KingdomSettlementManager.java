package org.fantasyrpg.kingdom.settlement;

import java.util.*;

/**
 * Строительство королевства игрока и управление поселением (раздел 9.2).
 * Автономные строители по чертежам, найм жителей, защита от набегов.
 */
public class KingdomSettlementManager {

    public static class Settlement {
        private final UUID settlementId;
        private final UUID ownerUUID;
        private String name;
        private int level = 1;
        private int population = 0;
        private final List<String> constructedBlueprints = new ArrayList<>();

        public Settlement(UUID settlementId, UUID ownerUUID, String name) {
            this.settlementId = settlementId;
            this.ownerUUID = ownerUUID;
            this.name = name;
        }

        public UUID getSettlementId() {
            return settlementId;
        }

        public UUID getOwnerUUID() {
            return ownerUUID;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }

        public int getPopulation() {
            return population;
        }

        public void addResident() {
            population++;
        }

        public void constructBlueprint(String blueprintName) {
            constructedBlueprints.add(blueprintName);
            if (constructedBlueprints.size() >= 3 && level == 1) {
                level = 2; // Уровень поселения растет при завершении построек
            }
        }

        public List<String> getConstructedBlueprints() {
            return Collections.unmodifiableList(constructedBlueprints);
        }
    }

    private static final KingdomSettlementManager INSTANCE = new KingdomSettlementManager();
    private final Map<UUID, Settlement> settlements = new LinkedHashMap<>();

    private KingdomSettlementManager() {}

    public static KingdomSettlementManager getInstance() {
        return INSTANCE;
    }

    public Settlement createSettlement(UUID ownerUUID, String name) {
        UUID id = UUID.randomUUID();
        Settlement settlement = new Settlement(id, ownerUUID, name);
        settlements.put(id, settlement);
        return settlement;
    }

    public Settlement getSettlement(UUID id) {
        return settlements.get(id);
    }

    public void clear() {
        settlements.clear();
    }
}
