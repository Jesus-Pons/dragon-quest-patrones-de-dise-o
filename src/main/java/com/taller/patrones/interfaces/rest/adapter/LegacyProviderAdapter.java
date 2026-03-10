package com.taller.patrones.interfaces.rest.adapter;

import java.util.Map;

public class LegacyProviderAdapter implements  FighterData{

    private final Map<String, Object> data;
    private final String prefix;

    private final String defaultName;
    private final int defaultHp;
    private final int defaultAtk;

    public LegacyProviderAdapter(Map<String, Object> data,String prefix,String defaultName, int defaultHp, int defaultAtk) {
        this.data = data;
        this.prefix = prefix;
        this.defaultName = defaultName;
        this.defaultHp = defaultHp;
        this.defaultAtk = defaultAtk;

    }


    @Override
    public String getName() {
        return (String) data.getOrDefault(prefix + "name", defaultName);
    }

    @Override
    public int getHp() {
        return ((Number) data.getOrDefault(prefix + "hp", defaultHp)).intValue();    }

    @Override
    public int getAtk() {
        return ((Number) data.getOrDefault(prefix + "atk", defaultAtk)).intValue();    }
}
