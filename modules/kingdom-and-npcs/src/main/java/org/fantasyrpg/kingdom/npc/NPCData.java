package org.fantasyrpg.kingdom.npc;

import java.util.UUID;

/**
 * Модель жителя поселения (NPC).
 * Включает профессию, диалог на русском языке, отношение к игроку (без эротики, раздел 8).
 */
public class NPCData {

    public enum Profession {
        BLACKSMITH("blacksmith", "Кузнец", "Могу выковать прочный меч или починить драконью броню!"),
        ALCHEMIST("alchemist", "Алхимик", "Зелья здоровья и экстракты маны всегда к вашим услугам."),
        GUARD("guard", "Стражник", "Я защищаю покой нашего поселения от монстров и бандитов."),
        BUILDER("builder", "Строитель", "Дайте мне чертеж и ресурсы — я возведу здание за пару минут!"),
        MERCHANT("merchant", "Купец", "Принимаю медные, серебряные и золотые монеты по честному курсу.");

        private final String id;
        private final String russianName;
        private final String defaultGreeting;

        Profession(String id, String russianName, String defaultGreeting) {
            this.id = id;
            this.russianName = russianName;
            this.defaultGreeting = defaultGreeting;
        }

        public String getId() {
            return id;
        }

        public String getRussianName() {
            return russianName;
        }

        public String getDefaultGreeting() {
            return defaultGreeting;
        }
    }

    private final UUID npcUUID;
    private final String name;
    private final Profession profession;
    private int relationshipPoints = 0; // -100 to +100

    public NPCData(UUID npcUUID, String name, Profession profession) {
        this.npcUUID = npcUUID;
        this.name = name;
        this.profession = profession != null ? profession : Profession.MERCHANT;
    }

    public UUID getNpcUUID() {
        return npcUUID;
    }

    public String getName() {
        return name;
    }

    public Profession getProfession() {
        return profession;
    }

    public int getRelationshipPoints() {
        return relationshipPoints;
    }

    public void adjustRelationship(int delta) {
        this.relationshipPoints = Math.min(100, Math.max(-100, this.relationshipPoints + delta));
    }

    public String interact() {
        return String.format("[%s %s]: %s (Отношение: %d)",
                profession.getRussianName(), name, profession.getDefaultGreeting(), relationshipPoints);
    }
}
