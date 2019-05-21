package cn.e3.sso.Controller;

import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.e3.sso.service.TokenService;
import util.E3Result;

@Controller
public class TokenController {
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(value="/user/token/{token}",
			produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public String tokenInfo(@PathVariable String token,String callback) {
		E3Result e3Result = tokenService.findToken(token);
		//判断是否为jsonp请求
		if(StringUtils.isNotBlank(callback)) {
			String strResult = callback + "(" + JSON.toJSONString(e3Result) + ");";
			return strResult;
		}
		return JSON.toJSONString(e3Result);
	}
}
