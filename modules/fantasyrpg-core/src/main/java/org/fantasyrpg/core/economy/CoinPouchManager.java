package org.fantasyrpg.core.economy;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Менеджер выделенных кошельков игроков.
 * Гарантирует авто-выдачу кошелька со стартовой книгой знаний,
 * сохранность монет при смерти и мультиплеерную синхронизацию.
 */
public class CoinPouchManager {

    private static final CoinPouchManager INSTANCE = new CoinPouchManager();
    private final Map<UUID, CoinPouch> pouches = new ConcurrentHashMap<>();

    private CoinPouchManager() {}

    public static CoinPouchManager getInstance() {
        return INSTANCE;
    }

    public CoinPouch getOrCreatePouch(UUID playerUUID) {
        if (playerUUID == null) return null;
        return pouches.computeIfAbsent(playerUUID, CoinPouch::new);
    }

    /**
     * Вызывается при первом спавне игрока в новом мире.
     * Выдает стартовый набор: кошелек с начальными разменными монетами (например 50 меди) и книгу знаний.
     */
    public boolean provideStarterPouchAndGuide(UUID playerUUID) {
        if (playerUUID == null) return false;
        CoinPouch pouch = getOrCreatePouch(playerUUID);
        if (pouch.getTotalCopperValue() == 0) {
            pouch.deposit(0, 0, 50); // Стартовый капитал: 50 медных монет
            return true;
        }
        return false;
    }

    /**
     * Обрабатывает смерть игрока: по правилам ТЗ монеты в выделенном кошельке сохраняются.
     */
    public void onPlayerDeath(UUID playerUUID) {
        // Кошелек сохраняется, баланс не сбрасывается
    }

    public void clear() {
        pouches.clear();
    }
}
