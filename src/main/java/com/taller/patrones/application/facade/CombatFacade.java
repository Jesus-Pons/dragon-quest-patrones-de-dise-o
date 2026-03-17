package com.taller.patrones.application.facade;

import com.taller.patrones.application.BattleService;
import com.taller.patrones.domain.Battle;

public class CombatFacade {

    private final BattleService battleService;

    public CombatFacade(BattleService battleService) {
        this.battleService = battleService;
    }
    public Battle executeTurn(String battleId, String playerAttackName) {
        Battle battle = battleService.getBattle(battleId);

        if (battle == null || battle.isFinished()) {
            return battle;
        }

        if (battle.isPlayerTurn()) {
            battleService.executePlayerAttack(battleId, playerAttackName);
        }

        if (!battle.isFinished() && !battle.isPlayerTurn()) {
            String enemyAttack = BattleService.ENEMY_ATTACKS.get((int) (Math.random() * BattleService.ENEMY_ATTACKS.size()));
            battleService.executeEnemyAttack(battleId, enemyAttack);
        }

        return battle;
    }
}