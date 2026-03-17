package com.taller.patrones.application.events;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Battle;
import com.taller.patrones.domain.Character;
public class AuditLogListener implements DamageListener {
    @Override
    public void onDamageDealt(Battle battle, Character attacker, Character defender, int damage, Attack attack) {
        battle.log(attacker.getName() + " usa " + attack.getName() + " y hace " + damage + " de daño a " + defender.getName());
    }
}
