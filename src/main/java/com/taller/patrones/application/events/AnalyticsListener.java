package com.taller.patrones.application.events;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Battle;
import com.taller.patrones.domain.Character;

public class AnalyticsListener implements DamageListener {

    @Override
    public void onDamageDealt(Battle battle, Character attacker, Character defender, int damage, Attack attack) {
        System.out.println("[ANALYTICS] DB Guardando evento: Daño en batalla (FAKE)");
    }
}
