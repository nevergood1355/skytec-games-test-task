package com.skytecgames.clan;

import com.skytecgames.core.AbstractEntity;

import java.util.Objects;

public class Clan extends AbstractEntity<Long> {

    private final String name;
    private Integer gold;

    public Clan(String name, Integer gold) {
        this.name = name;
        this.gold = gold;
    }

    public String getName() {
        return name;
    }

    public synchronized Integer getGold() {
        return gold;
    }

    public synchronized void addGold(int gold) {
        this.gold += gold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clan)) return false;
        Clan clan = (Clan) o;
        return Objects.equals(getId(), clan.getId()) &&
                name.equals(clan.name) &&
                gold.equals(clan.gold);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name, gold);
    }
}
