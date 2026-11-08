package org.fantasyrpg.kingdom.faction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Менеджер фракций и репутации (раздел 8 и 12).
 * Фракции: Орден Паладинов, Гильдия Магов, Лесной Клан, Разбойники Пустошей.
 */
public class FactionManager {

    public enum ReputationLevel {
        HOSTILE("hostile", "Враждебное отношение"),
        NEUTRAL("neutral", "Нейтральное отношение"),
        FRIENDLY("friendly", "Дружелюбное отношение"),
        HONORED("honored", "Почтенный союзник");

        private final String id;
        private final String russianName;

        ReputationLevel(String id, String russianName) {
            this.id = id;
            this.russianName = russianName;
        }

        public String getId() {
            return id;
        }

        public String getRussianName() {
            return russianName;
        }
    }

    private static final FactionManager INSTANCE = new FactionManager();
    private final Map<String, String> factions = new LinkedHashMap<>();
    private final Map<UUID, Map<String, Integer>> playerReputation = new ConcurrentHashMap<>();

    private FactionManager() {
        factions.put("paladins", "Орден Священного Света");
        factions.put("mages", "Магический Конклав");
        factions.put("rangers", "Стражи Диких Лесов");
        factions.put("merchants", "Торговый Союз Королевств");
    }

    public static FactionManager getInstance() {
        return INSTANCE;
    }

    public void adjustReputation(UUID playerUUID, String factionId, int delta) {
        if (playerUUID == null || factionId == null) return;
        Map<String, Integer> rep = playerReputation.computeIfAbsent(playerUUID, k -> new ConcurrentHashMap<>());
        int current = rep.getOrDefault(factionId.toLowerCase(), 0);
        rep.put(factionId.toLowerCase(), Math.min(3000, Math.max(-3000, current + delta)));
    }

    public int getReputation(UUID playerUUID, String factionId) {
        Map<String, Integer> rep = playerReputation.get(playerUUID);
        return rep != null ? rep.getOrDefault(factionId.toLowerCase(), 0) : 0;
    }

    public ReputationLevel getReputationLevel(UUID playerUUID, String factionId) {
        int score = getReputation(playerUUID, factionId);
        if (score < -500) return ReputationLevel.HOSTILE;
        if (score < 500) return ReputationLevel.NEUTRAL;
        if (score < 1500) return ReputationLevel.FRIENDLY;
        return ReputationLevel.HONORED;
    }

    public Map<String, String> getAllFactions() {
        return Collections.unmodifiableMap(factions);
    }
}
