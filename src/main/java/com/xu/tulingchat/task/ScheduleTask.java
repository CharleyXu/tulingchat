package com.xu.tulingchat.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务 @Scheduled的使用
 */
@Component
public class ScheduleTask {
    @Scheduled(cron = "0 48 14 * * ?")
    public void task(){
        System.out.println("开启定时任务："+ new Date());
    }
    @Scheduled(fixedRate = 1000*60*60*2)    //单位:ms
    public void taskjob(){
        System.out.println("--START----\n 定时任务:"+new Date());
    }

    /**
     *
     *
     * //定义一个按时间执行的定时任务，在每天16:00执行一次。
     @Scheduled(cron = "0 0 16 * * ?")
     public void depositJob() {
     //执行代码
     }
     //定义一个按一定频率执行的定时任务，每隔1分钟执行一次
     @Scheduled(fixedRate = 1000 * 60)
     public void job2() {
     //执行代码
     }
     //定义一个按一定频率执行的定时任务，每隔1分钟执行一次，延迟1秒执行
     @Scheduled(fixedRate = 1000 * 60,initialDelay = 1000)
     public void updatePayRecords() {
     //执行代码
     }
     *
     *
     */
}
