package cn.e3.Redistest;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.alibaba.dubbo.config.ApplicationConfig;

import cn.e3.redis.JedisClient;
import cn.e3.redis.JedisClientPool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class testBySpring {
	/**
	 * 单机版和集群版使用实现类，只是配置文件的差异
	 */
	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Spring/applicationContext-redis.xml");
		//JedisClientPool jedisClientPool =  (JedisClientPool) context.getBean("jedisClientPool");
		JedisClient jedisClient = context.getBean(JedisClient.class);
		jedisClient.set("today", "2-28");
		String result = jedisClient.get("today");
		System.out.println(result);
	}
	
}
