package org.fantasyrpg.world.tree;

/**
 * Механика рубки дерева целиком (Раздел 13 спецификации):
 * «Tree-felling mechanic: break one base log block and fell the whole tree;
 * receive combined wood/sticks/saplings/other tree drops. Axe durability must be
 * calculated as if all tree blocks were broken. Make the feature toggleable.»
 */
public class TreeFellingEngine {

    public static class FellingResult {
        private final boolean success;
        private final int logsHarvested;
        private final int sticksDropped;
        private final int saplingsDropped;
        private final int durabilityConsumed;
        private final String message;

        public FellingResult(boolean success, int logsHarvested, int sticksDropped, int saplingsDropped, int durabilityConsumed, String message) {
            this.success = success;
            this.logsHarvested = logsHarvested;
            this.sticksDropped = sticksDropped;
            this.saplingsDropped = saplingsDropped;
            this.durabilityConsumed = durabilityConsumed;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public int getLogsHarvested() {
            return logsHarvested;
        }

        public int getSticksDropped() {
            return sticksDropped;
        }

        public int getSaplingsDropped() {
            return saplingsDropped;
        }

        public int getDurabilityConsumed() {
            return durabilityConsumed;
        }

        public String getMessage() {
            return message;
        }
    }

    private static final TreeFellingEngine INSTANCE = new TreeFellingEngine();
    private boolean enabled = true;

    private TreeFellingEngine() {}

    public static TreeFellingEngine getInstance() {
        return INSTANCE;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Симулирует рубку дерева целиком.
     * @param totalLogsInTree общее количество древесных блоков в дереве
     * @param currentAxeDurability текущая прочность топора
     * @return результат сбора ресурсов и списания прочности
     */
    public FellingResult fellTree(int totalLogsInTree, int currentAxeDurability) {
        if (!enabled) {
            return new FellingResult(false, 1, 0, 0, 1, "Быстрая рубка отключена в настройках.");
        }

        if (totalLogsInTree <= 0) {
            return new FellingResult(false, 0, 0, 0, 0, "Дерево не обнаружено.");
        }

        // Прочность списывается точно за каждый срубленный блок древесины
        int logsToFell = Math.min(totalLogsInTree, Math.max(1, currentAxeDurability));
        int sticks = (int) Math.round(logsToFell * 0.4);
        int saplings = Math.max(1, (int) Math.round(logsToFell * 0.2));
        int durabilityConsumed = logsToFell;

        String msg = String.format("Срублено дерево целиком! Получено: %d бревен, %d палок, %d саженцев. Прочность топора: -%d.",
                logsToFell, sticks, saplings, durabilityConsumed);

        return new FellingResult(true, logsToFell, sticks, saplings, durabilityConsumed, msg);
    }
}
