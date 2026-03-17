package com.taller.patrones.application.events;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Battle;
import com.taller.patrones.domain.Character;

import java.util.ArrayList;
import java.util.List;

public class DamagePublisher {

    private final List<DamageListener > damageListenerList = new
            ArrayList<>();

    public void attackAction(Battle battle, Character attacker, Character defender, int damage, Attack attack) {
        damageListenerList .forEach(l -> l.onDamageDealt( battle, attacker, defender, damage, attack));
    }
    public void addListener (DamageListener listener ) {
        damageListenerList .add(listener);
    }
}
