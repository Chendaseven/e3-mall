package cn.e3.sso.service;


import cn.e3mall.pojo.TbUser;
import util.E3Result;

public interface SsoUserService {
	E3Result checkData(String param,int type);
	E3Result registerData(TbUser tbUser);
	E3Result login(String userAccount,String password);
	E3Result logout(String token);
}
