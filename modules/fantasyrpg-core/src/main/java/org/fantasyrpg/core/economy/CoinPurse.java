package org.fantasyrpg.core.economy;

/**
 * Универсальный кошелек и монетный слой.
 * Заменяет изумрудную торговлю на единую экономику:
 * 100 медных монет = 1 серебряная монета
 * 100 серебряных монет = 1 золотая монета
 * Согласовано с разделом 10 мастер-спецификации.
 */
public class CoinPurse {

    private long totalCopperUnits; // Хранение баланса в наименьшей неделимой единице (медь)

    public CoinPurse() {
        this.totalCopperUnits = 0;
    }

    public CoinPurse(int gold, int silver, int copper) {
        this.totalCopperUnits = (gold * 10000L) + (silver * 100L) + copper;
    }

    public void addCopper(long copper) {
        if (copper > 0) {
            this.totalCopperUnits += copper;
        }
    }

    public void addSilver(long silver) {
        if (silver > 0) {
            this.totalCopperUnits += (silver * 100L);
        }
    }

    public void addGold(long gold) {
        if (gold > 0) {
            this.totalCopperUnits += (gold * 10000L);
        }
    }

    public boolean withdrawCopper(long copper) {
        if (copper <= 0) return false;
        if (this.totalCopperUnits >= copper) {
            this.totalCopperUnits -= copper;
            return true;
        }
        return false;
    }

    public long getTotalCopperUnits() {
        return totalCopperUnits;
    }

    public int getGoldCoins() {
        return (int) (totalCopperUnits / 10000L);
    }

    public int getSilverCoins() {
        return (int) ((totalCopperUnits % 10000L) / 100L);
    }

    public int getCopperCoins() {
        return (int) (totalCopperUnits % 100L);
    }

    public String formatRussian() {
        return String.format("%d золотых, %d серебряных, %d медных монет",
                getGoldCoins(), getSilverCoins(), getCopperCoins());
    }
}
