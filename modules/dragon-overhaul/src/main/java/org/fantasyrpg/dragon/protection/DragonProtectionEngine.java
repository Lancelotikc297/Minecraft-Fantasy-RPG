package org.fantasyrpg.dragon.protection;

import java.util.HashSet;
import java.util.Set;

/**
 * Движок защиты построек игрока от разрушения драконами.
 * Реализует не подлежащее отмене требование раздела 6.2 мастер-спецификации:
 * «Player-built structures must be protected by default. Do not let a fleeing/roaming dragon accidentally destroy the player's house or settlement.»
 */
public class DragonProtectionEngine {

    public enum DestructionMode {
        DISABLED("disabled", "Полностью отключено"),
        LIMITED("limited", "Ограниченное разрушение"),
        ATTACKS_ONLY("attacks_only", "Только при прямой атаке"),
        FULL("full", "Полное разрушение (опасно)");

        private final String id;
        private final String russianName;

        DestructionMode(String id, String russianName) {
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

    private static final DragonProtectionEngine INSTANCE = new DragonProtectionEngine();
    private boolean protectPlayerBuilds = true; // СТРОГО ВКЛЮЧЕНО ПО УМОЛЧАНИЮ
    private DestructionMode destructionMode = DestructionMode.LIMITED;
    private final Set<String> protectedChunks = new HashSet<>();

    private DragonProtectionEngine() {}

    public static DragonProtectionEngine getInstance() {
        return INSTANCE;
    }

    public boolean isProtectPlayerBuilds() {
        return protectPlayerBuilds;
    }

    public void setProtectPlayerBuilds(boolean protect) {
        this.protectPlayerBuilds = protect;
    }

    public DestructionMode getDestructionMode() {
        return destructionMode;
    }

    public void setDestructionMode(DestructionMode destructionMode) {
        this.destructionMode = destructionMode;
    }

    public void addProtectedChunk(int chunkX, int chunkZ) {
        protectedChunks.add(chunkX + ":" + chunkZ);
    }

    public void removeProtectedChunk(int chunkX, int chunkZ) {
        protectedChunks.remove(chunkX + ":" + chunkZ);
    }

    public boolean isChunkProtected(int chunkX, int chunkZ) {
        return protectedChunks.contains(chunkX + ":" + chunkZ);
    }

    /**
     * Проверяет, разрешено ли дракону разрушить блок в указанной позиции чанка.
     * @param chunkX координата чанка X
     * @param chunkZ координата чанка Z
     * @param isPlayerBuild является ли блок частью постройки игрока
     * @param isDirectAttack происходит ли разрушение во время боя/атаки
     * @return true если разрушение разрешено, false если заблокировано защитой
     */
    public boolean canDragonDestroyBlock(int chunkX, int chunkZ, boolean isPlayerBuild, boolean isDirectAttack) {
        // 1. Постройки игрока защищены по умолчанию всегда
        if (protectPlayerBuilds && isPlayerBuild) {
            return false;
        }

        // 2. Блоки внутри зарегистрированных защищенных зон/поселений неприкосновенны
        if (isChunkProtected(chunkX, chunkZ)) {
            return false;
        }

        // 3. Проверка режима разрушений
        return switch (destructionMode) {
            case DISABLED -> false;
            case ATTACKS_ONLY -> isDirectAttack;
            case LIMITED, FULL -> true;
        };
    }
}
