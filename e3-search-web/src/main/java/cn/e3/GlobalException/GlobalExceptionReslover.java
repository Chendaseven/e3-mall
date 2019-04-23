package cn.e3.GlobalException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class GlobalExceptionReslover implements HandlerExceptionResolver{
	
	Logger logger = LoggerFactory.getLogger(GlobalExceptionReslover.class);
			
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// TODO Auto-generated method stub
		//写日志文件，日志配置文件见log4j.properties
		logger.debug("系统发生异常", ex);
		logger.info("系统错误", ex);
		logger.warn("系统严重错误", ex);
		
		//发邮件、发短信
		//使用Jmail
		//发送短信需要使用第三方接口，需要购买
		
		//展示错误页面
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "系统发生异常，请稍后再试");
		modelAndView.setViewName("erro/exception");
		return modelAndView;
	}
	
}
