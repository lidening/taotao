package com.taotao.rest.jedis;
import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	@Test
	public void TestJedisSingle(){
		Jedis jedis=new Jedis("192.168.25.157", 6379);
		jedis.set("key1","test");
		String string = jedis.get("key1");
		System.out.println(string);
		jedis.close();
		
	}
	@Test
	public void TestJedisPools(){
		JedisPool jedisPool=new JedisPool("192.168.25.157", 6379);
		Jedis jedis = jedisPool.getResource();
		jedis.set("key2", "test2");
		String string = jedis.get("key2");
		System.out.println(string);
		jedis.close();
		jedisPool.close();
	}
	@Test
	public void TestJedisCluster(){
		HashSet<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.157",7001));
		nodes.add(new HostAndPort("192.168.25.157",7002));
		nodes.add(new HostAndPort("192.168.25.157",7003));
		nodes.add(new HostAndPort("192.168.25.157",7004));
		nodes.add(new HostAndPort("192.168.25.157",7005));
		nodes.add(new HostAndPort("192.168.25.157",7006));
		JedisCluster cluster=new JedisCluster(nodes);
		cluster.set("key3", "test3");
		String string = cluster.get("key3");
		System.out.println(string);
		cluster.close();
		
	}
	@Test
	public void TestSpringRedisSingle(){
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool jedisPool =(JedisPool) applicationContext.getBean("redisClient");
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get("key2");
		System.out.println(string);
		jedis.close();
		jedisPool.close();
		
	}
	@Test
	public void TestSpringRedisCluster(){
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster cluster= (JedisCluster) applicationContext.getBean("redisClient");
		String string = cluster.get("key3");
		System.out.println(string);
		cluster.close();
	}
}
