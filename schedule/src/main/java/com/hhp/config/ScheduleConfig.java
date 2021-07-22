package com.hhp.config;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

//@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    private String cron = "0/1 * * * * ?";

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {

        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程："+ Thread.currentThread().getName() + " 在" + LocalDateTime.now() + "执行定时任务！");
            }
        };

        Trigger trigger = new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                CronTrigger cronTrigger = new CronTrigger(cron);
                Date nextExecutionTime = cronTrigger.nextExecutionTime(triggerContext);
                return nextExecutionTime;
            }
        };

        registrar.addTriggerTask(task, trigger);
    }
}
