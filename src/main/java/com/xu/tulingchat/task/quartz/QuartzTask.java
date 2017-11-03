package com.xu.tulingchat.task.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Quartz任务类
 */
public class QuartzTask implements Job {

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		// 执行响应的任务.
		System.out.println("QuartzTask.execute:任务类  当前时间: " + new Date());
	}
}
