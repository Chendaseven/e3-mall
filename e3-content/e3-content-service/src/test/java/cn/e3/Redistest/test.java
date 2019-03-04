package cn.e3.Redistest;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class test {
	/**
	 * 创建单机版，测试Jedis
	 */
	@Test
	public void redisTest() {
		//1、创建一个Jedis对象，需要指定服务端的ip及端口
		Jedis jedis = new Jedis("192.168.25.128",6379);
		//2、使用Jedis对象操作数据库，每个redis命令对应一个方法
		jedis.set("xyz", "12345");
		//3、打印结果
		String result = jedis.get("xyz");
		System.out.println(result);
		//4、关闭Jedis
		jedis.close();
	}
	
	/**
	 *创建单机版，使用连接池
	 */
	@Test
	public void JedisPoolTest() {
		//1、创建一个JedisPool对象，需要指定服务端ip和端口
		JedisPool jedisPool = new JedisPool("192.168.25.128",6379);
		//2、从JedisPool中获得Jedis对象
		Jedis jedis = jedisPool.getResource();
		//3、使用Jedis操作jedis对象
		jedis.set("abc", "789");
		//打印结果
		String result = jedis.get("abc");
		System.out.println(result);
		//4、操作完毕后关闭jedis对象，连接池回收资源
		jedis.close();
		//5、关闭JedisPool对象
		jedisPool.close();
	}
	
	/**
	 * 创建集群版，连接测试
	 */
	@Test
	public void JedisClusterTest() {
		//1、使用JedisCluster对象，需要一个Set<HostAndPort>参数作为Redis节点的列表
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.128",7001));
		nodes.add(new HostAndPort("192.168.25.128",7002));
		nodes.add(new HostAndPort("192.168.25.128",7003));
		nodes.add(new HostAndPort("192.168.25.128",7004));
		nodes.add(new HostAndPort("192.168.25.128",7005));
		nodes.add(new HostAndPort("192.168.25.128",7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		//2、直接使用JedisCluster对象操作redis，在系统中单例存在
		jedisCluster.set("hello", "100");
		String result = jedisCluster.get("hello");
		//3、打印结果
		System.out.println(result);
		//4、关闭Cluster对象
		jedisCluster.close();
	}
	
	
}
