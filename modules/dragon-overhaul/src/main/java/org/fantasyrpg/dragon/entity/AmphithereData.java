package org.fantasyrpg.dragon.entity;

import java.util.UUID;

/**
 * Амфиптерий (Amphithere) — юркий и маневренный летающий маунт (раздел 6.6).
 * Легче и быстрее драконов, имеет собственную легкую экипировку.
 */
public class AmphithereData {

    private final UUID entityUUID;
    private int health = 80;
    private int maxHealth = 80;
    private double flightSpeed = 1.6; // Быстрее базового дракона (1.2)
    private boolean tamed = false;
    private UUID ownerUUID = null;
    private boolean hasSaddle = false;

    public AmphithereData(UUID entityUUID) {
        this.entityUUID = entityUUID;
    }

    public UUID getEntityUUID() {
        return entityUUID;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public double getFlightSpeed() {
        return flightSpeed;
    }

    public boolean isTamed() {
        return tamed;
    }

    public void tame(UUID ownerUUID) {
        this.tamed = true;
        this.ownerUUID = ownerUUID;
    }

    public boolean hasSaddle() {
        return hasSaddle;
    }

    public void setHasSaddle(boolean hasSaddle) {
        this.hasSaddle = hasSaddle;
    }

    public String renderStatus() {
        return String.format("Амфиптерий: Здоровье %d/%d HP | Скорость полета: %.1fx | Седло: %s",
                health, maxHealth, flightSpeed, hasSaddle ? "Надето" : "Отсутствует");
    }
}
