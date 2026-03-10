package com.taller.patrones.infrastructure.combat;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Character;
import com.taller.patrones.infrastructure.combat.factory.AttackFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Motor de combate. Calcula daño y crea ataques.
 * <p>
 * Nota: Esta clase crece cada vez que añadimos un ataque nuevo o un tipo de daño distinto.
 */
public class CombatEngine {
    private final  AttackFactory attackFactory;
    private final Map<Attack.AttackType, DamageStrategy> damageStrategies;

    public CombatEngine() {
        this.attackFactory = new AttackFactory();
        this.damageStrategies = new HashMap<>();
        this.damageStrategies.put(Attack.AttackType.NORMAL, new NormalDamageStrategy());
        this.damageStrategies.put(Attack.AttackType.SPECIAL, new SpecialDamageStrategy());
        this.damageStrategies.put(Attack.AttackType.STATUS, new StatusDamageStrategy());
    }

    public Attack createAttack(String name) {
        return attackFactory.createAttack(name);
    }

    /**
     * Calcula el daño según el tipo de ataque.
     * Cada fórmula nueva (ej. crítico, veneno con tiempo) requiere modificar este switch.
     */
    public int calculateDamage(Character attacker, Character defender, Attack attack) {
        DamageStrategy strategy = damageStrategies.get(attack.getType());
        
        if(strategy != null) {
            return strategy.calculateDamage(attacker,defender,attack);
        }
        return 0;
    }
}
