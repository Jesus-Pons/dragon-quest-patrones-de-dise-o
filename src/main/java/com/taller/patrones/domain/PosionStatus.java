package com.taller.patrones.domain;

public class PosionStatus implements StatusEffect {
    @Override
    public String getName() {
        return "Veneno";
    }

    @Override
    public int process(Character target) {
        int damage = 10;
        target.takeDamage(damage);
        return damage;
    }
}
