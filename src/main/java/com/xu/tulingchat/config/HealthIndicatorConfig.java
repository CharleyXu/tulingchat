package com.xu.tulingchat.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author CharleyXu Created on 2017/12/24. 监控配置
 */
@Component
public class HealthIndicatorConfig implements HealthIndicator {

	@Override
	public Health health() {
		int errorCode = check();
		if (errorCode != 0) {
			return Health.down().withDetail("Error Code", errorCode).build();
		}
		return Health.up().build();
	}

	private int check() {
		// 对监控对象的检测操作
		return 0;
	}
}
