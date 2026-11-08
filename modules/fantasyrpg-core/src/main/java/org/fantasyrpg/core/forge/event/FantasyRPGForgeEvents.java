package org.fantasyrpg.core.forge.event;

import org.fantasyrpg.core.FantasyRPGCore;
import org.fantasyrpg.core.command.RPGCommandHandler;
import org.fantasyrpg.core.data.PlayerRPGData;
import org.fantasyrpg.core.data.RPGDataManager;
import org.fantasyrpg.core.economy.CoinPurse;

import java.util.UUID;

/**
 * Центральный обработчик игровых событий Forge.
 * Связывает жизненный цикл игрока, получение урона, смерть мобов и команды с RPG-ядром.
 */
public class FantasyRPGForgeEvents {

    /**
     * Вызывается при входе игрока на сервер (PlayerLoggedInEvent).
     */
    public static void onPlayerLoggedIn(UUID playerUUID, String playerName) {
        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        System.out.printf("[FantasyRPG] Игрок %s (%s) подключился. Загружен уровень %d [%s].%n",
                playerName, playerUUID, data.getLevel(), data.getCharacterClass().getRussianName());
    }

    /**
     * Вызывается при респауне игрока (PlayerRespawnEvent).
     * Сохраняет ролевой прогресс и восстанавливает ману при правиле keepInventory/защиты.
     */
    public static void onPlayerRespawn(UUID playerUUID, boolean endConquered) {
        PlayerRPGData data = RPGDataManager.getInstance().getPlayerData(playerUUID);
        if (FantasyRPGCore.getInstance().getConfig().isProtectPlayerBuilds()) {
            data.setCurrentMana(data.getMaxMana());
        }
    }

    /**
     * Вызывается при нанесении урона сущности (LivingHurtEvent).
     * Применяет бонусы силы атакующего, шанс критического удара от ловкости и снижение урона броней.
     */
    public static float onLivingHurt(UUID attackerUUID, float baseDamage, int targetArmor, boolean targetBlocking) {
        PlayerRPGData attacker = attackerUUID != null ? RPGDataManager.getInstance().getPlayerData(attackerUUID) : null;
        if (attacker == null) {
            return baseDamage;
        }

        // Влияние силы на физический урон
        double strengthBonus = 1.0 + ((attacker.getStrength() - 10) * 0.04);
        double weaponScaled = baseDamage * strengthBonus;

        // Влияние ловкости на крит (5% + 0.5% за очко ловкости выше 10)
        double critChance = 0.05 + Math.max(0, (attacker.getAgility() - 10) * 0.005);
        if (Math.random() < critChance) {
            weaponScaled *= 1.5;
        }

        // Снижение броней цели
        double armorReduction = Math.min(0.80, targetArmor * 0.02);
        double finalDamage = weaponScaled * (1.0 - armorReduction);

        if (targetBlocking) {
            finalDamage *= 0.30;
        }

        return (float) Math.max(1.0, finalDamage);
    }

    /**
     * Вызывается при гибели монстра от руки игрока (LivingDeathEvent).
     * Начисляет опыт и чеканные монеты в зависимости от силы противника.
     */
    public static void onLivingDeath(UUID killerUUID, String mobName, double maxHealth, boolean isBoss) {
        if (killerUUID == null) return;

        PlayerRPGData killer = RPGDataManager.getInstance().getPlayerData(killerUUID);
        CoinPurse purse = RPGCommandHandler.getPlayerPurse(killerUUID);

        int xp = (int) Math.max(5, maxHealth * 1.5);
        int coins = (int) Math.max(1, maxHealth * 0.8);

        if (isBoss) {
            xp *= 4;
            coins *= 5;
        }

        killer.addExperience(xp);
        purse.addCopper(coins);
    }
}
