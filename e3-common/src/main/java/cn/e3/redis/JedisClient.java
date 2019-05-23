package cn.e3.redis;

import java.util.List;

public interface JedisClient {
	//String型设置key值
	String set(String key, String value);
	//String型取值
	String get(String key);
	//阐述key值
	String delete(String key);
	//判断是否存在此key
	Boolean exists(String key);
	//设置key值过期时间，设置成功返回1，当 key 不存在或者不能为 key 设置过期时间时返回0
	Long expire(String key, int seconds);
	//返回key的剩余过期时间
	Long ttl(String key);
	//为key存储的数字值加1，如果键 key 不存在， 那么它的值会先被初始化为 0 ， 然后再执行 INCR 命令，如果键 key 储存的值不能被解释为数字， 那么 INCR 命令将返回一个错误
	Long incr(String key);
	//为hash表赋值
	Long hset(String key, String field, String value);
	//取hash值
	String hget(String key, String field);
	//删除hash表一个或多个指定key值域
	Long hdel(String key, String... field);
	//取所有key值下的value集合
	List<String> hvals(String key);
}
