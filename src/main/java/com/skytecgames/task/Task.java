package com.skytecgames.task;

import com.skytecgames.core.AbstractEntity;

public class Task extends AbstractEntity<Long> {
    private final String desc;
    private final Integer award;
    private boolean success;


    public Task(String desc, Integer award, boolean success) {
        this.desc = desc;
        this.award = award;
        this.success = success;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getAward() {
        return award;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
