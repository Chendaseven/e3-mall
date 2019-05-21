package cn.e3.sso.service.impl;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.e3.redis.JedisClient;
import cn.e3.sso.service.SsoUserService;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import util.E3Result;

@Service
public class SsoUserServiceImpl implements SsoUserService{

	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private JedisClient jedisClient;
	
	/**
	 * 对传入的注册用户数据进行检查
	 */
	@Override
	public E3Result checkData(String param, int type) {
		TbUserExample tbUserExample = new TbUserExample();
		Criteria criteria = tbUserExample.createCriteria();
		//type=1表示用户名，type=2表示手机号
		if(type==1) {
			//表示搜索的条件
			criteria.andUsernameEqualTo(param);
		}else if(type==2) {
			criteria.andPhoneEqualTo(param);
		}else {
			return E3Result.build(400, "非法数据");
		}
		List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);
		//判断搜索结果，如果查询到数据，那么返回false
		if(list == null || list.size() == 0) {
			return E3Result.ok(true);
		}
		return E3Result.ok(false);
	}
	
	
	@Override
	public E3Result registerData(TbUser tbUser) {
		Date date = new Date();
		//密码加密
		String passwrd = DigestUtils.md5Hex(tbUser.getPassword());
		tbUser.setPassword(passwrd);
		//设置用户createdTime和updatedtime
		tbUser.setCreated(date);
		tbUser.setUpdated(date);
		//保存用户数据
		int insert = tbUserMapper.insert(tbUser);
		if(insert != 0) {
			return E3Result.ok();
		}else {
			return null;
		}
	}


	@Override
	public E3Result login(String userAccount, String password) {
		//将password MD5加密
		String token = null;
		String pwd = DigestUtils.md5Hex(password);
		TbUser tbUser = tbUserMapper.findUserByAccountAndPwd(userAccount, pwd);
		if(tbUser != null) {
			//如果用户不为空
			//将用户数据放入session中，此处用redis替代session
			tbUser.setPassword("");
			//将数据存储在redis中
			token = UUID.randomUUID().toString();
			jedisClient.set("SESSION:"+token, JSON.toJSONString(tbUser));
			//设置key过期时间
			jedisClient.expire("SESSION:"+token, 1800);
			return E3Result.ok(token);
		}else {
			return E3Result.build(300, "用户名或密码错误");
		}

	}

	/**
	 * 用户退出登陆方法
	 */
	
	@Override
	public E3Result logout(String token) {
		//将redis中token删除
		if(StringUtils.isNoneBlank(token)) {
			jedisClient.delete("SESSION:"+token);
		}else {
			//如果redis中没有用户信息，说明已经过期
			return E3Result.ok();
		}
		return E3Result.ok();
		
	}






	
}
