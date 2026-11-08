package org.fantasyrpg.world.backpack;

import java.util.HashMap;
import java.util.Map;

/**
 * Модульный рюкзак большой вместимости (Раздел 13 спецификации):
 * «Modular backpacks inspired by Sophisticated Backpacks:
 * crafting/workbench/furnace/etc. modules and very large stack capacity».
 */
public class BackpackSystem {

    public enum BackpackTier {
        LEATHER("leather", "Кожаный рюкзак", 27, 64),
        IRON("iron", "Железный рюкзак", 54, 256),
        GOLD("gold", "Золотой рюкзак", 81, 1024),
        DIAMOND("diamond", "Алмазный рюкзак", 108, 4096),
        NETHERITE("netherite", "Незеритовый рюкзак", 120, 16384);

        private final String id;
        private final String russianName;
        private final int slots;
        private final int maxStackMultiplier;

        BackpackTier(String id, String russianName, int slots, int maxStackMultiplier) {
            this.id = id;
            this.russianName = russianName;
            this.slots = slots;
            this.maxStackMultiplier = maxStackMultiplier;
        }

        public String getId() {
            return id;
        }

        public String getRussianName() {
            return russianName;
        }

        public int getSlots() {
            return slots;
        }

        public int getMaxStackMultiplier() {
            return maxStackMultiplier;
        }
    }

    private final BackpackTier tier;
    private final Map<String, Integer> contents = new HashMap<>();
    private boolean craftingModuleInstalled = false;
    private boolean autoSortingInstalled = true;

    public BackpackSystem(BackpackTier tier) {
        this.tier = tier != null ? tier : BackpackTier.LEATHER;
    }

    public BackpackTier getTier() {
        return tier;
    }

    public boolean isCraftingModuleInstalled() {
        return craftingModuleInstalled;
    }

    public void setCraftingModuleInstalled(boolean installed) {
        this.craftingModuleInstalled = installed;
    }

    public boolean isAutoSortingInstalled() {
        return autoSortingInstalled;
    }

    public boolean addItem(String itemId, int count) {
        if (itemId == null || count <= 0) return false;
        int current = contents.getOrDefault(itemId, 0);
        int maxCapacity = tier.getMaxStackMultiplier();
        if (current + count > maxCapacity) {
            return false;
        }
        contents.put(itemId, current + count);
        return true;
    }

    public int getItemCount(String itemId) {
        return contents.getOrDefault(itemId, 0);
    }

    public String renderRussianSummary() {
        return String.format("[%s]: Слотов: %d | Вместимость: %d на слот | Верстак: %s | Автосортировка: %s",
                tier.getRussianName(), tier.getSlots(), tier.getMaxStackMultiplier(),
                craftingModuleInstalled ? "Установлен" : "Нет", autoSortingInstalled ? "Активна" : "Нет");
    }
}
