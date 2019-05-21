package cn.e3.sso.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.e3.sso.service.SsoUserService;
import util.CookieUtils;
import util.E3Result;

@Controller
public class LogoutController {
	
	@Autowired
	private SsoUserService ssoUserService;
	
	@RequestMapping("/page/logout/{token}")
	@ResponseBody
	public Object logoutPage(@PathVariable String token,String callback,
			HttpServletRequest request,HttpServletResponse response) {
		E3Result e3Result = ssoUserService.logout(token);
		//删除浏览器中的cookie
		CookieUtils.deleteCookie(request, response, "token");
		if(StringUtils.isNoneBlank(callback)) {
			String strResult = callback + "(" + JSON.toJSONString(e3Result) + ");";
			return strResult;
//			也采用了MappingJacksonValue解决jsonp跨域调用问题
//			//创建MappingJacksonValue对象进行result对象包装
//            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
//            //配置回调函数名
//            mappingJacksonValue.setJsonpFunction(callback);
//            return mappingJacksonValue;

		}
		return JSON.toJSONString(e3Result);
	}
	
}
