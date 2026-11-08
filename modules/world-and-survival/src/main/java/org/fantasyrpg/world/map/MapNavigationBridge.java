package org.fantasyrpg.world.map;

import java.util.*;

/**
 * Интеграционный мост с картографией (Раздел 14 спецификации):
 * Интеграция функционала Xaero's Minimap & World Map: миникарта, карта мира, путевые точки,
 * координаты и телепортация с серверным контролем разрешений.
 */
public class MapNavigationBridge {

    public static class Waypoint {
        private final String name;
        private final int x, y, z;
        private final String dimension;

        public Waypoint(String name, int x, int y, int z, String dimension) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.z = z;
            this.dimension = dimension;
        }

        public String getName() {
            return name;
        }

        public int getX() { return x; }
        public int getY() { return y; }
        public int getZ() { return z; }
        public String getDimension() { return dimension; }
    }

    private static final MapNavigationBridge INSTANCE = new MapNavigationBridge();
    private final Map<UUID, List<Waypoint>> playerWaypoints = new HashMap<>();
    private boolean serverTeleportAllowed = true;

    private MapNavigationBridge() {}

    public static MapNavigationBridge getInstance() {
        return INSTANCE;
    }

    public void addWaypoint(UUID playerUUID, String name, int x, int y, int z, String dimension) {
        if (playerUUID == null || name == null) return;
        List<Waypoint> list = playerWaypoints.computeIfAbsent(playerUUID, k -> new ArrayList<>());
        list.add(new Waypoint(name, x, y, z, dimension));
    }

    public List<Waypoint> getWaypoints(UUID playerUUID) {
        return playerWaypoints.getOrDefault(playerUUID, Collections.emptyList());
    }

    public boolean isServerTeleportAllowed() {
        return serverTeleportAllowed;
    }

    public void setServerTeleportAllowed(boolean allowed) {
        this.serverTeleportAllowed = allowed;
    }
}
