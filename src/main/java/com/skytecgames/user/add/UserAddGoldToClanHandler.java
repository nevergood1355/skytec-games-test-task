package com.skytecgames.user.add;

import com.skytecgames.audit.AuditActionType;
import com.skytecgames.audit.AuditService;
import com.skytecgames.clan.Clan;
import com.skytecgames.clan.ClanService;

import java.util.Optional;

public class UserAddGoldToClanHandler {

    private final ClanService clans;
    private final AuditService auditService;

    public UserAddGoldToClanHandler(ClanService clans, AuditService auditService) {
        this.clans = clans;
        this.auditService = auditService;
    }

    public void addGoldToClan(Long userId, Long clanId, int value) {
        Optional<Clan> clan = clans.get(clanId);
        clan.ifPresent(it -> {
            synchronized (it.getGold()) {
                it.addGold(value);
                audit(userId, clanId, String.format("Add gold(%d) to clan %d. The clan has %d", value, clanId, it.getGold()));
            }
        });
    }

    private void audit(Long userId, Long clanId, String desc) {
        auditService.audit(userId, clanId, desc, AuditActionType.ADD_GOLD_TO_CLAN);
    }
}
