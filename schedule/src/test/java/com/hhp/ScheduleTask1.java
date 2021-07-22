package com.hhp;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

public class ScheduleTask1 implements Runnable{

    private String cron = "0/2 * * * * ?";
    TaskScheduler taskScheduler = new DefaultManagedTaskScheduler();

    @Override
    public void run() {
//        ScheduledFuture<?> future = taskScheduler.schedule(() -> {
//            System.out.println("ScheduleTask1--->线程：" + Thread.currentThread().getName() + " 在"
//                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
//                    + "执行了任务！");
//        }, new CronTrigger(cron));
        System.out.println(Thread.currentThread().getName() + "-->future1");
    }
}
