package com.skytecgames.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.Serializable;

public class AuditRecord implements Serializable {

    private static final ObjectMapper toJson = new JsonMapper();

    /* Audit timestamp in ms*/
    private final Long timestamp;
    private final Long initiatorId;
    private final Long clanId;
    private final String desc;
    private final AuditActionType actionType;


    public AuditRecord(Long initiatorId, Long clanId, String desc, AuditActionType actionType) {
        this.initiatorId = initiatorId;
        this.clanId = clanId;
        this.desc = desc;
        this.actionType = actionType;
        this.timestamp = System.currentTimeMillis();
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Long getInitiatorId() {
        return initiatorId;
    }

    public Long getClanId() {
        return clanId;
    }

    public String getDesc() {
        return desc;
    }

    public AuditActionType getActionType() {
        return actionType;
    }

    @Override
    public String toString() {
        try {
            return toJson.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return String.format("[JSON] record error:%s", e.getMessage());
        }
    }
}
