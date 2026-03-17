package com.taller.patrones.application.events;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Battle;
import com.taller.patrones.domain.Character;

public interface DamageListener {
    void onDamageDealt(Battle battle, Character attacker, Character defender, int damage, Attack attack);
}
