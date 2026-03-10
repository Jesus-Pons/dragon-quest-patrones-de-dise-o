package com.taller.patrones.domain;

/**
 * Representa un ataque que puede ejecutar un personaje.
 */
public class Attack {

    private final String name;
    private final int basePower;
    private final AttackType type;
    private final StatusEffect effect;

    public Attack(String name, int basePower, AttackType type) {
        this.name = name;
        this.basePower = basePower;
        this.type = type;
        this.effect = null;
    }

    public Attack(String name, int basePower, AttackType type,StatusEffect effect) {
        this.name = name;
        this.basePower = basePower;
        this.type = type;
        this.effect = effect;
    }

    public String getName() { return name; }
    public int getBasePower() { return basePower; }
    public AttackType getType() { return type; }
    public StatusEffect getEffect() { return effect; }
    public enum AttackType {
        NORMAL, SPECIAL, STATUS
    }
}
