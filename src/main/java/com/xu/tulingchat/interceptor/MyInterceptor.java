package com.xu.tulingchat.interceptor;

import com.xu.tulingchat.util.IPAddressUtil;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器，可以实现IP黑名单等
 */
public class MyInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Object o) throws Exception {
	  //在请求处理之前进行调用（Controller方法调用之前）,验证权限，使用ThreadLocal等
	  //System.out.println("preHandle:在Controller之前调用,权限验证");
	  return true;
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest,
						 HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView)
		  throws Exception {
	  // 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
	  System.out.println("ClientIpAddress:" + IPAddressUtil.getClientIpAddress(httpServletRequest));
  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
	  //在视图渲染完成之后调用 在整个请求结束之后被调用  清除ThreadLocal等
  }

}
