package cn.e3.sso.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.sso.service.SsoUserService;
import cn.e3mall.pojo.TbUser;
import util.E3Result;

@Controller
public class RegisterController {
	@Autowired
	private SsoUserService ssoUserService;
	
	@RequestMapping("/page/register")
	public String register() {
		return "register";
	}
	/**
	 * 检查申请的账户名及手机号码是否重复
	 * @param param
	 * @param type
	 * @return
	 */
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public E3Result checkData(@PathVariable String param,@PathVariable int type) {
		E3Result e3Result = ssoUserService.checkData(param, type);
		return e3Result;
	}
	
	/**
	 * 保存用户数据
	 * @return
	 */
	@RequestMapping("/user/register")
	@ResponseBody
	public E3Result saveData(TbUser tbUser) {
		E3Result e3Result = ssoUserService.registerData(tbUser);
		return e3Result;
	}
	
	
	
	//注册页面跳转到登陆页面
	@RequestMapping("/page/login")
	public String loginPage() {
		return "login";
	}
	
}
