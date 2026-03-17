package com.taller.patrones.domain;

import java.util.List;
import java.util.stream.Collectors;

public class ComboAttack extends Attack{

    private final List<Attack> attacks;

    public ComboAttack(String name, List<Attack> attacks){
        super(name, 0, AttackType.NORMAL);
        this.attacks = attacks;
    }

    @Override
    public int getBasePower() {
        return attacks.stream()
                .mapToInt(Attack::getBasePower)
                .sum();
    }

    @Override
    public String getName() {
        String comboDetail = attacks.stream()
                .map(Attack::getName)
                .collect(Collectors.joining(" + "));
        return super.getName() + " [" + comboDetail + "]";
    }

    @Override
    public AttackType getType() {
        if (!attacks.isEmpty()) {
            return attacks.get(0).getType();
        }
        return AttackType.NORMAL;
    }

}
