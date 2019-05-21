package cn.e3.sso.service;

import util.E3Result;

public interface TokenService {
	E3Result findToken(String token);
}
