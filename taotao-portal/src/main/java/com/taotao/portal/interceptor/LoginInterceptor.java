package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.InterceptorService;
import com.taotao.portal.service.impl.InterceptorServiceImpl;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private InterceptorServiceImpl interceptorService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			//获取cookie中token
			String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
			//调用sso接口，获取session
			TbUser tbUser = interceptorService.getUserByToken(token);
			if(null==tbUser){
				//取不到用户session时，跳转到登录页面，并传递拦截url参数，登录后跳转到拦截的页面
				response.sendRedirect(interceptorService.SSO_BASE_URL+interceptorService.SSO_PAGE_LOGIN+"?redirect="+request.getRequestURL());
				return false;
			}
			//获取cookie中user信息，返回时添加到request中，为生成订单时添加用户信息
			request.setAttribute("user", tbUser);
		} catch (Exception e) {
			e.printStackTrace();
			TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
