package cn.e3.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.e3.redis.JedisClient;
import cn.e3.sso.service.TokenService;
import cn.e3mall.pojo.TbUser;
import util.E3Result;

@Service
public class TokenServiceImpl implements TokenService {
	
	@Autowired
	private JedisClient jedisClient;
	
	/**
	 * 根据token在redis中取信息
	 */
	@Override
	public E3Result findToken(String token) {
		//根据token查信息
		String userString = jedisClient.get("SESSION:"+token);
		//如果查不到数据，返回用户已经过期
		if(userString == null) {
			return E3Result.build(401, "用户登陆过期，请重新登陆");
		}else {
			//如果能够查询到用户数据，那么重置key过期时间
			jedisClient.expire("SESSION:"+token, 1800);
			//将数据转换为TbUser对象并返回
			TbUser tbUser = JSON.parseObject(userString,TbUser.class);
			return E3Result.ok(tbUser);
		}
	}

}
