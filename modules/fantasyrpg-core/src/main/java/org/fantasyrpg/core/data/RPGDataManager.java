package org.fantasyrpg.core.data;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Реестр хранения данных игроков RPG в памяти сервера.
 */
public class RPGDataManager {

    private static final RPGDataManager INSTANCE = new RPGDataManager();
    private final Map<UUID, PlayerRPGData> playerDataMap = new ConcurrentHashMap<>();

    private RPGDataManager() {}

    public static RPGDataManager getInstance() {
        return INSTANCE;
    }

    public PlayerRPGData getPlayerData(UUID uuid) {
        if (uuid == null) return null;
        return playerDataMap.computeIfAbsent(uuid, PlayerRPGData::new);
    }

    public void removePlayerData(UUID uuid) {
        if (uuid != null) {
            playerDataMap.remove(uuid);
        }
    }

    public void clear() {
        playerDataMap.clear();
    }
}
