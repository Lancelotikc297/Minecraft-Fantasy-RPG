package org.fantasyrpg.kingdom.quest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Квестовая система: сюжетные задания, охота за головами и поручения жителей (раздел 10.2).
 */
public class QuestManager {

    public static class Quest {
        private final String id;
        private final String title;
        private final String description;
        private final int rewardXP;
        private final int rewardCopper;
        private final String targetFaction;
        private final int targetFactionRep;

        public Quest(String id, String title, String description, int rewardXP, int rewardCopper, String targetFaction, int targetFactionRep) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.rewardXP = rewardXP;
            this.rewardCopper = rewardCopper;
            this.targetFaction = targetFaction;
            this.targetFactionRep = targetFactionRep;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public int getRewardXP() {
            return rewardXP;
        }

        public int getRewardCopper() {
            return rewardCopper;
        }

        public String getTargetFaction() {
            return targetFaction;
        }

        public int getTargetFactionRep() {
            return targetFactionRep;
        }
    }

    private static final QuestManager INSTANCE = new QuestManager();
    private final Map<String, Quest> quests = new LinkedHashMap<>();
    private final Map<UUID, Set<String>> completedQuests = new ConcurrentHashMap<>();

    private QuestManager() {
        registerDefaultQuests();
    }

    public static QuestManager getInstance() {
        return INSTANCE;
    }

    private void registerDefaultQuests() {
        register(new Quest("starter_slayer", "Очистка окрестностей", "Победите 5 скелетов в окрестностях поселения", 150, 200, "paladins", 100));
        register(new Quest("dragon_investigation", "Следы чудовищ", "Исследуйте логово древнего огненного дракона в горах", 500, 1000, "merchants", 250));
        register(new Quest("magic_runes", "Тайна древних свитков", "Соберите 3 зачарованных свитка для магов конклава", 300, 450, "mages", 150));
    }

    public void register(Quest q) {
        quests.put(q.getId().toLowerCase(), q);
    }

    public Quest getQuest(String id) {
        return quests.get(id.toLowerCase());
    }

    public Collection<Quest> getAllQuests() {
        return Collections.unmodifiableCollection(quests.values());
    }

    public boolean completeQuest(UUID playerUUID, String questId) {
        Quest q = getQuest(questId);
        if (q == null) return false;

        Set<String> completed = completedQuests.computeIfAbsent(playerUUID, k -> new HashSet<>());
        return completed.add(q.getId());
    }

    public boolean isQuestCompleted(UUID playerUUID, String questId) {
        Set<String> completed = completedQuests.get(playerUUID);
        return completed != null && completed.contains(questId.toLowerCase());
    }

    public void clear() {
        completedQuests.clear();
    }
}
