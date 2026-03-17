package com.taller.patrones.application.command;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Battle;
import com.taller.patrones.domain.Character;

public class AttackCommand implements Command {

    private final Battle battle;
    private final Character attacker;
    private final Character defender;
    private final int damage;
    private final Attack attack;

    public AttackCommand(Battle battle, Character attacker, Character defender, int damage, Attack attack) {
        this.battle = battle;
        this.attacker = attacker;
        this.defender = defender;
        this.damage = damage;
        this.attack = attack;
    }

    @Override
    public void execute() {
        defender.takeDamage(damage);
        String target = defender == battle.getPlayer() ? "player" : "enemy";
        battle.setLastDamage(damage, target);

        battle.log(attacker.getName() + " usa " + attack.getName() + " y hace " + damage + " de daño a " + defender.getName());
    }

    @Override
    public void undo() {
        defender.heal(damage);
        battle.log("⏪ Se ha deshecho el ataque de " + attacker.getName() + ". " + defender.getName() + " recupera " + damage + " HP.");

        battle.switchTurn();
    }
}