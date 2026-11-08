package org.fantasyrpg.core.combat;

import org.fantasyrpg.core.data.PlayerRPGData;
import org.fantasyrpg.core.item.ItemRarity;

/**
 * Боевой движок расчета физического урона, критов, блокирования и выносливости.
 */
public class CombatEngine {

    public static class AttackResult {
        private final int damage;
        private final boolean critical;
        private final boolean blocked;
        private final String message;

        public AttackResult(int damage, boolean critical, boolean blocked, String message) {
            this.damage = damage;
            this.critical = critical;
            this.blocked = blocked;
            this.message = message;
        }

        public int getDamage() {
            return damage;
        }

        public boolean isCritical() {
            return critical;
        }

        public boolean isBlocked() {
            return blocked;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Рассчитывает урон атаки игрока по цели с учетом оружия, характеристик и редкости.
     */
    public static AttackResult calculatePhysicalAttack(
            PlayerRPGData attacker,
            int baseWeaponDamage,
            ItemRarity rarity,
            int targetArmor,
            boolean targetBlocking,
            double rollRandom
    ) {
        if (attacker == null) {
            return new AttackResult(baseWeaponDamage, false, false, "Обычный удар");
        }

        // Влияние силы на физический урон
        double strengthBonus = 1.0 + ((attacker.getStrength() - 10) * 0.04);
        double weaponScaled = baseWeaponDamage * rarity.getStatMultiplier() * strengthBonus;

        // Влияние ловкости на шанс крита (базовый 5% + 0.5% за единицу ловкости выше 10)
        double critChance = 0.05 + Math.max(0, (attacker.getAgility() - 10) * 0.005);
        boolean isCrit = rollRandom < critChance;
        if (isCrit) {
            weaponScaled *= 1.5;
        }

        // Расчет снижения урона броней
        double armorReduction = Math.min(0.80, targetArmor * 0.02); // максимум 80% снижение
        double finalDamage = weaponScaled * (1.0 - armorReduction);

        // Блокирование щитом
        boolean blocked = false;
        if (targetBlocking) {
            finalDamage *= 0.30; // блокируется 70% входящего урона
            blocked = true;
        }

        int damageDealt = (int) Math.max(1, Math.round(finalDamage));
        String desc = String.format("Удар нанес %d урона%s%s",
                damageDealt,
                isCrit ? " [КРИТИЧЕСКИЙ УДАР!]" : "",
                blocked ? " (Заблокировано щитом)" : "");

        return new AttackResult(damageDealt, isCrit, blocked, desc);
    }
}
