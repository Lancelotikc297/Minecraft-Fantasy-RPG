package org.fantasyrpg.core.economy;

import java.util.UUID;

/**
 * Выделенный ролевой кошелек игрока (Coin Pouch).
 * Автоматически выдается при старте мира, сохраняется после смерти,
 * автоматически принимает монеты и производит конвертацию номиналов (100 меди = 1 серебро, 100 серебра = 1 золото).
 */
public class CoinPouch {

    private final UUID playerUUID;
    private long totalCopperValue;
    private boolean autoConvert = true;

    public CoinPouch(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.totalCopperValue = 0;
    }

    public CoinPouch(UUID playerUUID, long initialCopper) {
        this.playerUUID = playerUUID;
        this.totalCopperValue = Math.max(0, initialCopper);
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    /**
     * Пополняет кошелек монетами.
     */
    public void deposit(int gold, int silver, int copper) {
        long added = (gold * 10000L) + (silver * 100L) + copper;
        if (added > 0) {
            this.totalCopperValue += added;
        }
    }

    public void depositCopper(long copper) {
        if (copper > 0) {
            this.totalCopperValue += copper;
        }
    }

    /**
     * Списывает сумму в меди. Возвращает true при успехе.
     */
    public boolean withdrawCopper(long copper) {
        if (copper <= 0) return false;
        if (totalCopperValue >= copper) {
            totalCopperValue -= copper;
            return true;
        }
        return false;
    }

    public long getTotalCopperValue() {
        return totalCopperValue;
    }

    public int getGold() {
        return (int) (totalCopperValue / 10000L);
    }

    public int getSilver() {
        return (int) ((totalCopperValue % 10000L) / 100L);
    }

    public int getCopper() {
        return (int) (totalCopperValue % 100L);
    }

    public boolean isAutoConvert() {
        return autoConvert;
    }

    public void setAutoConvert(boolean autoConvert) {
        this.autoConvert = autoConvert;
    }

    /**
     * Форматированный вывод баланса на русском языке.
     */
    public String renderRussianBalance() {
        return String.format("%d золотых, %d серебряных, %d медных монет (Всего в меди: %d)",
                getGold(), getSilver(), getCopper(), totalCopperValue);
    }
}
