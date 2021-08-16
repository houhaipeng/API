package com.hhp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestSchedule {
    private static BlockingQueue<Runnable> threadQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
//        String cron = "0/2 * * * * ?";
//        TaskScheduler taskScheduler = new DefaultManagedTaskScheduler();
//        ScheduledFuture<?> future = taskScheduler.schedule(() -> {
//            System.out.println("线程：" + Thread.currentThread().getName() + " 在"
//                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
//                    + "执行了任务！");
//        }, new CronTrigger(cron));
//        threadQueue.add(new ScheduleTask1());
//        threadQueue.add(new ScheduleTask2());
//        threadQueue.add(new ScheduleTask1());
//        threadQueue.take();
//        threadQueue.add(new ScheduleTask2());
//        for (int i = 0; i < 3; i++) {
//            new Thread(threadQueue.take(),"T" + i).start();
//        }
//        log.info("------------------------------");
        threadQueue.add(new ScheduleTask1());
        threadQueue.add(new ScheduleTask2());
        threadQueue.add(new ScheduleTask1());
        threadQueue.add(new ScheduleTask2());
        threadQueue.add(new ScheduleTask1());
        threadQueue.add(new ScheduleTask2());
        threadQueue.add(new ScheduleTask1());
        threadQueue.add(new ScheduleTask2());

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                3,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(5),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );
        int size = threadQueue.size();
        System.out.println(size);
        try {
            while (threadQueue.size() != 0) {
                threadPool.execute(threadQueue.take());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
