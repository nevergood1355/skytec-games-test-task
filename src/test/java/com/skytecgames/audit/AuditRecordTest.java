package com.skytecgames.audit;

import org.junit.Test;

import static org.junit.Assert.*;

public class AuditRecordTest {

    @Test
    public void testToString() {
        AuditRecord auditRecord = new AuditRecord(1L, 2L, "Test desc", AuditActionType.ADD_GOLD_TO_CLAN);
        assertEquals(String.format("{\"timestamp\":%d,\"initiatorId\":1,\"clanId\":2,\"desc\":\"Test desc\",\"actionType\":\"ADD_GOLD_TO_CLAN\"}", auditRecord.getTimestamp()), auditRecord.toString());
        System.out.println(auditRecord);
    }
}