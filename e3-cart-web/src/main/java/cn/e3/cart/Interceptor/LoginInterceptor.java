package cn.e3.cart.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3.sso.service.TokenService;
import cn.e3mall.pojo.TbUser;
import util.CookieUtils;
import util.E3Result;
/**
 * 判断用户是否登陆的拦截器
 * @ClassName LoginInterceptor
 * @Description 
 * @Author Chen Peng
 * @Date 2019年5月21日 下午9:25:51
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//前处理，执行handler之前执行此方法
		//返回true，放行，false：拦截
		//1、从cookie中取token
		String token = CookieUtils.getCookieValue(request, "token", true);
		if(StringUtils.isBlank(token)) {
			//2、如果没有token，未登陆状态，直接放行
			return true;
		}else {
			E3Result e3Result = tokenService.findToken(token);
			//3、取到token，需要调用sso系统的服务，根据token取用户信息
			if(e3Result.getStatus()==200) {
				TbUser tbUser = (TbUser) e3Result.getData();
				//4、把用户信息放到request中，只需要在Controller中判断request中是否包含user信息，放行
				request.setAttribute("tbUser", tbUser);
			}
		}
		//5、没有取到用户信息，登陆过期，直接放行
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//handler执行之后，返回ModelAndView之前

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//完成处理，返回ModelAndView之后，
		//可以在此处理异常

	}

}
