package com.xu.tulingchat.config;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author charlie Created on 2017/12/26. AOP日志统计
 */
@Aspect
@Component
@Order(5)
public class WebLogAspectConfig {

	private static Logger LOGGER = LoggerFactory.getLogger(WebLogAspectConfig.class);
	private ThreadLocal<Long> logtime = new ThreadLocal<>();

	/**
	 * 定义切入点 controller包下的所有函数
	 */
	@Pointcut("execution(* com.xu.tulingchat.controller.*.*(..))")
	public void webLog() {

	}

	/**
	 * 前置通知	, 接收请求并记录请求内容
	 */
	@Before("webLog()")
	public void invokeBefore(JoinPoint point) {
		logtime.set(System.currentTimeMillis());

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		LOGGER.info("URL : " + request.getRequestURL().toString());
		LOGGER.info("HTTP_METHOD : " + request.getMethod());
		LOGGER.info("IP : " + request.getRemoteAddr());
		LOGGER.info("CLASS_METHOD : " + getMethodName(point));
		LOGGER.info("ARGS : " + Arrays.toString(point.getArgs()));
	}

	/**
	 * 后置通知 , 处理完请求，返回内容
	 */
	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void invokeAfter(Object ret) throws Throwable {
		LOGGER.info("RESPONSE : " + ret);
		LOGGER.info("SPEND_TIME : " + (System.currentTimeMillis() - logtime.get()));
		LOGGER.info("------------------------------------------------");
	}

	/**
	 * 获得被代理对象的真实类全名 + 代理执行的方法名 point.getTarget().getClass().getName()和point.getSignature().getDeclaringTypeName()
	 * 结果一致
	 *
	 * @param point 连接点对象
	 * @return 类全名 + 调用方法名
	 */
	private String getMethodName(JoinPoint point) {
		if (null != point) {
			return point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();
		}
		return null;
	}

}
