package com.skytecgames.audit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AuditService {

    private final List<AuditRecord> records = new ArrayList<>();

    public void audit(Long userId, Long clanId, String desc, AuditActionType actionType) {
        records.add(new AuditRecord(userId, clanId, desc, actionType));
    }

    public List<AuditRecord> getHistoryByClanId(Long clanId) {
        return records.stream()
                .filter(it -> Objects.equals(it.getClanId(), clanId))
                .sorted(Comparator.comparingLong(AuditRecord::getTimestamp))
                .collect(Collectors.toList());
    }

}
