package com.skytecgames.task.handler;

import com.skytecgames.audit.AuditActionType;
import com.skytecgames.audit.AuditService;
import com.skytecgames.clan.Clan;
import com.skytecgames.clan.ClanService;
import com.skytecgames.task.Task;
import com.skytecgames.task.TaskService;

import java.util.Optional;

public class CompleteTaskHandler {

    private final ClanService clans;
    private final TaskService tasks;
    private final AuditService auditService;


    public CompleteTaskHandler(ClanService clans, TaskService tasks, AuditService auditService) {
        this.clans = clans;
        this.tasks = tasks;
        this.auditService = auditService;
    }

    public void completeTask(Long clanId, Long taskId) {
        Optional<Clan> clanOtp = clans.get(clanId);
        if (clanOtp.isPresent()) {
            Clan clan = clanOtp.get();
            Optional<Task> taskOtp = tasks.get(taskId);
            if (taskOtp.isPresent()) {
                Task task = taskOtp.get();
                if (!task.isSuccess()) {
                    task.setSuccess(true);
                    synchronized (clan.getGold()) {
                        clan.addGold(task.getAward());
                        audit(clanId, String.format("Add gold(%d) to clan %d. The clan has %d", task.getAward(), clanId, clan.getGold()));
                    }
                }
            }
        }
    }

    private void audit(long clanId, String desc) {
        auditService.audit(0L, clanId, desc, AuditActionType.COMPLETE_TASK);
    }

}
