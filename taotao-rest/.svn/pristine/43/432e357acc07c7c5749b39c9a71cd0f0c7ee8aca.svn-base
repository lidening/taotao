package com.taotao.rest.dao;

public interface JedisClient {
	String get(String key);
	String set(String key,String value);
	String hget(String hkey,String key);
	long hset(String hkye,String key,String value);
	long incr(String key);
	long expire(String key,int secend);
	long ttl(String key);
	long del(String key);
	long hdel(String hkey,String key);
}
