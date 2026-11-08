package org.fantasyrpg.core.graphics;

/**
 * Менеджер графических профилей и оптимизации (Phase G).
 * Предоставляет 3 профиля: Ноутбук (Laptop), Сбалансированный (Balanced), Кинематографичный (Cinematic).
 * Настраивает лимиты частиц, дальность симуляции и агрессивный куллинг сущностей.
 */
public class GraphicsProfileManager {

    public enum Profile {
        LAPTOP("laptop", "Ноутбук / Энергосбережение", 8, 0.4, false, true),
        BALANCED("balanced", "Сбалансированный (60 FPS)", 12, 1.0, true, true),
        CINEMATIC("cinematic", "Кинематографичный (Максимум)", 18, 1.5, true, false);

        private final String id;
        private final String russianName;
        private final int renderDistanceChunks;
        private final double particleMultiplier;
        private final boolean dynamicLighting;
        private final boolean aggressiveEntityCulling;

        Profile(String id, String russianName, int renderDistanceChunks, double particleMultiplier, boolean dynamicLighting, boolean aggressiveEntityCulling) {
            this.id = id;
            this.russianName = russianName;
            this.renderDistanceChunks = renderDistanceChunks;
            this.particleMultiplier = particleMultiplier;
            this.dynamicLighting = dynamicLighting;
            this.aggressiveEntityCulling = aggressiveEntityCulling;
        }

        public String getId() {
            return id;
        }

        public String getRussianName() {
            return russianName;
        }

        public int getRenderDistanceChunks() {
            return renderDistanceChunks;
        }

        public double getParticleMultiplier() {
            return particleMultiplier;
        }

        public boolean isDynamicLighting() {
            return dynamicLighting;
        }

        public boolean isAggressiveEntityCulling() {
            return aggressiveEntityCulling;
        }
    }

    private static final GraphicsProfileManager INSTANCE = new GraphicsProfileManager();
    private Profile activeProfile = Profile.BALANCED;

    private GraphicsProfileManager() {}

    public static GraphicsProfileManager getInstance() {
        return INSTANCE;
    }

    public Profile getActiveProfile() {
        return activeProfile;
    }

    public void setProfile(Profile profile) {
        if (profile != null) {
            this.activeProfile = profile;
        }
    }

    public String renderStatus() {
        return String.format("Профиль графики: [%s] | Прорисовка: %d чанков | Частицы: %.1fx | Динамический свет: %s | Куллинг: %s",
                activeProfile.getRussianName(),
                activeProfile.getRenderDistanceChunks(),
                activeProfile.getParticleMultiplier(),
                activeProfile.isDynamicLighting() ? "Вкл" : "Выкл",
                activeProfile.isAggressiveEntityCulling() ? "Агрессивный" : "Стандартный");
    }
}
