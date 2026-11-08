package org.fantasyrpg.world.boss;

/**
 * Движок многофазных боссов (Раздел 5 и 11 спецификации).
 * Поддерживает 3 боевые фазы: обычная атака, ярость (увеличенный урон и скорость), финальный щит/призыв миньонов.
 */
public class BossEncounterEngine {

    public enum BossPhase {
        PHASE_1_NORMAL(1, "Фаза 1: Обычный бой"),
        PHASE_2_ENRAGED(2, "Фаза 2: Исступление (+50% урона)"),
        PHASE_3_DESPERATION(3, "Фаза 3: Драконий щит и вихрь");

        private final int phaseNumber;
        private final String russianName;

        BossPhase(int phaseNumber, String russianName) {
            this.phaseNumber = phaseNumber;
            this.russianName = russianName;
        }

        public int getPhaseNumber() {
            return phaseNumber;
        }

        public String getRussianName() {
            return russianName;
        }
    }

    private final String bossId;
    private final String bossRussianName;
    private final int maxHealth;
    private int currentHealth;
    private BossPhase currentPhase = BossPhase.PHASE_1_NORMAL;

    public BossEncounterEngine(String bossId, String bossRussianName, int maxHealth) {
        this.bossId = bossId;
        this.bossRussianName = bossRussianName;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void applyDamage(int damage) {
        if (damage <= 0) return;
        this.currentHealth = Math.max(0, this.currentHealth - damage);
        updatePhase();
    }

    private void updatePhase() {
        double healthPercent = (double) currentHealth / maxHealth;
        if (healthPercent > 0.60) {
            currentPhase = BossPhase.PHASE_1_NORMAL;
        } else if (healthPercent > 0.25) {
            currentPhase = BossPhase.PHASE_2_ENRAGED;
        } else if (currentHealth > 0) {
            currentPhase = BossPhase.PHASE_3_DESPERATION;
        }
    }

    public BossPhase getCurrentPhase() {
        return currentPhase;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public String getBossRussianName() {
        return bossRussianName;
    }

    public boolean isDefeated() {
        return currentHealth <= 0;
    }
}
