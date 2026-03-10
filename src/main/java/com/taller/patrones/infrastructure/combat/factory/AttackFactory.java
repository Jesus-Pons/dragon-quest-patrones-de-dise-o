package com.taller.patrones.infrastructure.combat.factory;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.PosionStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AttackFactory {
    private final Map<String, Supplier<Attack>> attackRegistry = new HashMap<>();

    public AttackFactory() {
        registerAttack("TACKLE", () -> new Attack("Tackle", 40, Attack.AttackType.NORMAL));
        registerAttack("SLASH", () -> new Attack("Slash", 55, Attack.AttackType.NORMAL));
        registerAttack("FIREBALL", () -> new Attack("Fireball", 80, Attack.AttackType.SPECIAL));
        registerAttack("ICE_BEAM", () -> new Attack("Ice Beam", 70, Attack.AttackType.SPECIAL));
        registerAttack("POISON_STING", () -> new Attack("Poison Sting", 20, Attack.AttackType.STATUS, new PosionStatus()));
        registerAttack("THUNDER", () -> new Attack("Thunder", 90, Attack.AttackType.SPECIAL));
        registerAttack("METEORO", () -> new Attack("Meteoro", 120, Attack.AttackType.SPECIAL));
    }

    public void registerAttack(String name, Supplier<Attack> attackSupplier) {
        attackRegistry.put(name.toUpperCase(), attackSupplier);
    }

    public Attack createAttack(String name) {
        String attackName = name != null ? name.toUpperCase() : "";
        Supplier<Attack> attackSupplier = attackRegistry.get(attackName);

        if (attackSupplier != null) {
            return attackSupplier.get();
        }
        return new Attack("Golpe", 30, Attack.AttackType.NORMAL);
    }
}
