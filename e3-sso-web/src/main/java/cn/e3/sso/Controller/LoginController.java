package cn.e3.sso.Controller;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
 * 用户登陆controller
 * @ClassName LoginController
 * @Description 
 * @Author Chen Peng
 * @Date 2019年5月14日 下午10:51:27
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.sso.service.SsoUserService;
import util.CookieUtils;
import util.E3Result;
@Controller
public class LoginController {
	@Autowired
	private SsoUserService ssoUserService;
	
	@RequestMapping("/user/login")
	@ResponseBody
	public E3Result login(String username,String password,
			HttpServletRequest request,HttpServletResponse response) {
		E3Result e3Result = ssoUserService.login(username, password);
		if(e3Result.getStatus()==200) {
			String token = e3Result.getData().toString();
			//如果登陆成功把token写入cookie
			CookieUtils.setCookie(request, response, "token", token);
		}
		return e3Result;
	}
	
}
