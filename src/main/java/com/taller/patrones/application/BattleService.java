package com.taller.patrones.application;

import com.taller.patrones.application.events.AnalyticsListener;
import com.taller.patrones.application.events.AuditLogListener;
import com.taller.patrones.application.events.DamagePublisher;
import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Battle;
import com.taller.patrones.domain.Character;
import com.taller.patrones.infrastructure.combat.CombatEngine;
import com.taller.patrones.infrastructure.persistence.BattleRepository;
import com.taller.patrones.interfaces.rest.adapter.FighterData;

import java.util.List;
import java.util.UUID;

/**
 * Caso de uso: gestionar batallas.
 * <p>
 * Nota: Crea sus propias dependencias con new. Cada vez que necesitamos
 * un CombatEngine o BattleRepository, hacemos new aquí.
 */
public class BattleService {

    private final CombatEngine combatEngine = new CombatEngine();
    private final BattleRepository battleRepository = BattleRepository.getInstance();
    private final DamagePublisher damagePublisher= new DamagePublisher();
    public static final List<String> PLAYER_ATTACKS = List.of("TACKLE", "SLASH", "FIREBALL", "ICE_BEAM", "POISON_STING", "THUNDER","METEORO");
    public static final List<String> ENEMY_ATTACKS = List.of("TACKLE", "SLASH", "FIREBALL");

    public BattleService() {
        this.damagePublisher.addListener(new AuditLogListener());
        this.damagePublisher.addListener(new AnalyticsListener());
    }


    public BattleStartResult startBattle(FighterData playerData, FighterData enemyData) {
        Character player = Character.builder()
                .name(playerData.getName())
                .maxHp(playerData.getHp())
                .attack(playerData.getAtk())
                .defense(15)
                .speed(20)
                .build();

        Character enemy = Character.builder()
                .name(enemyData.getName())
                .maxHp(enemyData.getHp())
                .attack(enemyData.getAtk())
                .defense(10)
                .speed(15)
                .build();

        Battle battle = new Battle(player, enemy);
        String battleId = UUID.randomUUID().toString();
        battleRepository.save(battleId, battle);

        return new BattleStartResult(battleId, battle);
    }

    public Battle getBattle(String battleId) {
        return battleRepository.findById(battleId);
    }

    public void executePlayerAttack(String battleId, String attackName) {
        Battle battle = battleRepository.findById(battleId);
        if (battle == null || battle.isFinished() || !battle.isPlayerTurn()) return;

        Attack attack = combatEngine.createAttack(attackName);
        int damage = combatEngine.calculateDamage(battle.getPlayer(), battle.getEnemy(), attack);
        applyDamage(battle, battle.getPlayer(), battle.getEnemy(), damage, attack);
    }

    public void executeEnemyAttack(String battleId, String attackName) {
        Battle battle = battleRepository.findById(battleId);
        if (battle == null || battle.isFinished() || battle.isPlayerTurn()) return;

        Attack attack = combatEngine.createAttack(attackName != null ? attackName : "TACKLE");
        int damage = combatEngine.calculateDamage(battle.getEnemy(), battle.getPlayer(), attack);
        applyDamage(battle, battle.getEnemy(), battle.getPlayer(), damage, attack);
    }

    private void applyDamage(Battle battle, Character attacker, Character defender, int damage, Attack attack) {
        defender.takeDamage(damage);
        String target = defender == battle.getPlayer() ? "player" : "enemy";
        battle.setLastDamage(damage, target);
        damagePublisher.attackAction(battle,attacker,defender,damage,attack);
        int statusDamage = attacker.processStatus();
        if(statusDamage > 0){
            battle.log(attacker.getName() + " sufre " + statusDamage + " puntos de daño por " + attacker.getActiveStatus().getName() + ".");
        }
        battle.switchTurn();
        if (!defender.isAlive()) {
            battle.finish(attacker.getName());
        }
    }

    public BattleStartResult startBattleFromExternal(String fighter1Name, int fighter1Hp, int fighter1Atk,
                                                     String fighter2Name, int fighter2Hp, int fighter2Atk) {
        Character player = Character.builder()
                .name(fighter1Name)
                .maxHp(fighter1Hp)
                .attack(fighter1Atk)
                .defense(10)
                .speed(10)
                .build();
        Character enemy = Character.builder()
                .name(fighter2Name)
                .maxHp(fighter2Hp)
                .attack(fighter2Atk)
                .defense(10)
                .speed(10)
                .build();
        Battle battle = new Battle(player, enemy);
        String battleId = UUID.randomUUID().toString();
        battleRepository.save(battleId, battle);
        return new BattleStartResult(battleId, battle);
    }

    public record BattleStartResult(String battleId, Battle battle) {}
}
