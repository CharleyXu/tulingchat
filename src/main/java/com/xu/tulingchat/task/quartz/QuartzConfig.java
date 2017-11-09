package com.xu.tulingchat.task.quartz;

import com.xu.tulingchat.util.MusicUtil;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 启动时加载数据
 *
 * @Order 注解的执行优先级是按value值从小到大顺序。 要实现Job的动态管理，Job必须要持久化到数据库
 */
@Component
@Order(value = 1)
public class QuartzConfig implements CommandLineRunner {
	private Scheduler scheduler = null;

	@Override
	public void run(String... strings) throws Exception {
		//初始化MusicUtil的InitialsList
		for (int i = 65; i <= 90; i++) {
			MusicUtil.InitialsList.add(i);
		}
		System.out.println("InitialsList:" + MusicUtil.InitialsList);
		try {
			/*
			 * 在 Quartz 中， scheduler 由 SchedulerFactory创建：DirectSchedulerFactory 或者
			 * StdSchedulerFactory。第二种工厂 StdSchedulerFactory 使用较多，因为
			 * DirectSchedulerFactory 使用起来不够方便，需要作许多详细的手工编码设置。
			 */
			// 获取Scheduler实例
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
//			System.out.println("scheduler.start");
//			System.out.println(scheduler.getSchedulerName());
			// 注册任务
			JobDetail jobDetail = JobBuilder.newJob(QuartzTask.class).withIdentity("QuartzTaskJob", "QuartzTaskGroup").build();
			// 设置出发时间(每1天执行1次)
			SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(24).repeatForever();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger", "group").startNow().withSchedule(simpleScheduleBuilder).build();
			// 交由Scheduler安排触发
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
