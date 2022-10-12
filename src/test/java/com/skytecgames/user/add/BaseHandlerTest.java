package com.skytecgames.user.add;

import com.skytecgames.audit.AuditService;
import com.skytecgames.clan.Clan;
import com.skytecgames.clan.ClanService;
import com.skytecgames.task.Task;
import com.skytecgames.task.TaskService;
import com.skytecgames.task.handler.CompleteTaskHandler;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;

public class BaseHandlerTest {

    private static ClanService clanService;
    private static TaskService taskService;
    private static AuditService auditService;
    private static final Random r = new Random();

    @BeforeClass
    public static void setUp() {
        clanService = new ClanService();
        taskService = new TaskService();
        auditService = new AuditService();

        //region gen Clans
        String[] clanNames = {"Макларен",
                "Уоллес",
                "Мактавиш",
                "Гамильтон",
                "Макрей",
                "МакТаввишь"};
        for (String clanName : clanNames) {
            clanService.save(new Clan(clanName, 0));
        }
        //endregion

        //region gen Tasks
        String[] taskDescSample = {"Получить меч дракона",
                "Построить 10лвл дворца",
                "Сходить туда-то принести то-то",
                "Построить кузницу",
                "Построить форт",
                "Обучение"};
        for (String desc : taskDescSample) {
            taskService.save(new Task(desc, desc.length() * r.nextInt(5), false));
        }
        //endregion

    }

    @Test
    public void addGoldToClan() throws InterruptedException {
        UserAddGoldToClanHandler handler = new UserAddGoldToClanHandler(clanService, auditService);

        for (int i = 0; i < 100; i++) {
            (new Thread(() -> {
                Long userId = (long) Math.abs(UUID.randomUUID().hashCode());
                handler.addGoldToClan(userId, r.nextInt(6) + 1L, r.nextInt(100));
            })).start();
        }

        while (Thread.activeCount() > 2) {
            System.out.println("Processing...");
            Thread.sleep(100L);
        }

        //Получаем историю аудита по одному клану, чтобы было легче проверять атомарность
        auditService.getHistoryByClanId(1L).forEach(System.out::println);
    }

    @Test
    public void addGoldToClanAndCompleteTaskTest() throws InterruptedException {
        UserAddGoldToClanHandler addGoldToClanHandler = new UserAddGoldToClanHandler(clanService, auditService);
        CompleteTaskHandler completeTaskHandler = new CompleteTaskHandler(clanService, taskService, auditService);

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                Long userId = (long) Math.abs(UUID.randomUUID().hashCode());
                addGoldToClanHandler.addGoldToClan(userId, r.nextInt(6) + 1L, r.nextInt(100));
            }).start();
        }

        for (int i = 0; i < 100; i++) {
            new Thread(() -> completeTaskHandler
                    .completeTask(r.nextInt(6) + 1L, r.nextInt(6) + 1L)
            ).start();
        }

        while (Thread.activeCount() > 2) {
            System.out.println("Processing...");
            Thread.sleep(100L);
        }

        //Получаем историю аудита по одному клану, чтобы было легче проверять атомарность
        auditService.getHistoryByClanId(1L).forEach(System.out::println);
    }
}