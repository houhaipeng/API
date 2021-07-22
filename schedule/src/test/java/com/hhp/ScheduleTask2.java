package com.hhp;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

public class ScheduleTask2 implements Runnable{

    private String cron = "0/1 * * * * ?";
    TaskScheduler taskScheduler = new ConcurrentTaskScheduler();

    @Override
    public void run() {
//        ScheduledFuture<?> future = taskScheduler.schedule(() -> {
//            System.out.println("ScheduleTask2--->线程：" + Thread.currentThread().getName() + " 在"
//                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
//                    + "执行了任务！");
//        }, new CronTrigger(cron));
        System.out.println(Thread.currentThread().getName() + "-->future2");
    }
}
