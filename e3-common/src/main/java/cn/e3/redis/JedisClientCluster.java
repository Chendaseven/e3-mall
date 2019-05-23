package cn.e3.redis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.JedisCluster;
/**
 * Jedis集群使用jedisCluster API
 * @ClassName JedisClientCluster
 * @Description 
 * @Author Chen Peng
 * @Date 2019年5月15日 上午8:58:59
 */
public class JedisClientCluster implements JedisClient {
	
	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public Boolean exists(String key) {
		return jedisCluster.exists(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		return jedisCluster.expire(key, seconds);
	}

	@Override
	public Long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	@Override
	public Long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public Long hset(String key, String field, String value) {
		return jedisCluster.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {
		return jedisCluster.hget(key, field);
	}

	@Override
	public Long hdel(String key, String... field) {
		return jedisCluster.hdel(key, field);
	}

	@Override
	public String delete(String key) {
		jedisCluster.del(key);
		return null;
	}

	@Override
	public List<String> hvals(String key) {
		List<String> hvals = jedisCluster.hvals(key);
		return hvals;
	}

}
