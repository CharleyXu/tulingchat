package com.xu.tulingchat.task;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 配置异步任务分发  异步调用配置
 */
@Configuration
@EnableScheduling
@EnableAsync
public class ExecutorConfig {

	@Value("${sms.executor.corePoolSize}")
	private int corePoolSize;

	@Value("${sms.executor.maxPoolSize}")
	private int maxPoolSize;

	@Value("${sms.executor.queueCapacity}")
	private int queueCapacity;

	@Value("${song.executor.corePoolSize}")
	private int songCorePoolSize;

	@Value("${song.executor.maxPoolSize}")
	private int songMaxPoolSize;

	@Value("${song.executor.queueCapacity}")
	private int songQueueCapacity;

	@Bean(name = "mailAsync")
	public Executor mailAsync() {
		return asyncConfig(corePoolSize, maxPoolSize, queueCapacity, "-MailExecutor-");
	}


	@Bean(name = "songAsync")
	public Executor songAsync() {
		return asyncConfig(songCorePoolSize, songMaxPoolSize, songQueueCapacity, "-SongExecutor-");
	}

	/**
	 * @param threadNamePrefix -MailExecutor-
	 */
	public Executor asyncConfig(int corePoolSize, int maxPoolSize, int queueCapacity, String threadNamePrefix) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setThreadNamePrefix(threadNamePrefix);
		executor.initialize();
		return executor;
	}
}
