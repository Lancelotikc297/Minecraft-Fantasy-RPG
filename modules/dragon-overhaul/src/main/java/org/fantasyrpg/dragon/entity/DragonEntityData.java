package org.fantasyrpg.dragon.entity;

import java.util.UUID;

/**
 * Модель данных дракона: стадия роста (1-5), здоровье, вид, приручение, команды.
 * Согласовано с разделами 6.4 и 6.5 мастер-спецификации.
 */
public class DragonEntityData {

    public enum DragonCommand {
        STAY("stay", "Оставаться на месте"),
        FOLLOW("follow", "Следовать за хозяином"),
        GUARD("guard", "Охранять территорию"),
        WANDER("wander", "Свободный полет");

        private final String id;
        private final String russianName;

        DragonCommand(String id, String russianName) {
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

    private final UUID dragonUUID;
    private final DragonSpecies species;
    private int stage = 1; // 1 to 5
    private int ageInDays = 0;
    private int maxHealth;
    private int currentHealth;
    private boolean tamed = false;
    private UUID ownerUUID = null;
    private DragonCommand command = DragonCommand.STAY;
    private boolean passiveUnlessOrdered = true; // Не нападать без приказа

    public DragonEntityData(UUID dragonUUID, DragonSpecies species) {
        this.dragonUUID = dragonUUID;
        this.species = species != null ? species : DragonSpecies.FIRE;
        updateStatsForStage();
    }

    public void setStage(int stage) {
        this.stage = Math.min(5, Math.max(1, stage));
        updateStatsForStage();
    }

    private void updateStatsForStage() {
        // Здоровье зависит от стадии: 1: 50 HP, 2: 120 HP, 3: 250 HP, 4: 400 HP, 5: 600 HP
        this.maxHealth = switch (stage) {
            case 1 -> 50;
            case 2 -> 120;
            case 3 -> 250;
            case 4 -> 400;
            case 5 -> 600;
            default -> 100;
        };
        this.currentHealth = maxHealth;
    }

    public UUID getDragonUUID() {
        return dragonUUID;
    }

    public DragonSpecies getSpecies() {
        return species;
    }

    public int getStage() {
        return stage;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = Math.min(maxHealth, Math.max(0, currentHealth));
    }

    public boolean isTamed() {
        return tamed;
    }

    public void tame(UUID ownerUUID) {
        this.tamed = true;
        this.ownerUUID = ownerUUID;
        this.command = DragonCommand.FOLLOW;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public DragonCommand getCommand() {
        return command;
    }

    public void setCommand(DragonCommand command) {
        this.command = command != null ? command : DragonCommand.STAY;
    }

    public boolean isPassiveUnlessOrdered() {
        return passiveUnlessOrdered;
    }

    public void setPassiveUnlessOrdered(boolean passive) {
        this.passiveUnlessOrdered = passive;
    }

    /**
     * Формирует русскоязычную информационную панель статуса дракона (раздел 6.8).
     */
    public String renderStatusPanel() {
        return String.format("""
                === ПАНЕЛЬ СТАТУСА ДРАКОНА ===
                Вид: %s
                Стадия роста: %d (Максимальное здоровье: %d HP)
                Текущее здоровье: %d/%d HP
                Статус приручения: %s
                Текущий приказ: %s
                Режим защиты: %s
                """,
                species.getRussianName(),
                stage,
                maxHealth,
                currentHealth,
                maxHealth,
                tamed ? "Приручен" : "Дикий",
                command.getRussianName(),
                passiveUnlessOrdered ? "Не нападать без команды" : "Агрессивная защита"
        );
    }
}
