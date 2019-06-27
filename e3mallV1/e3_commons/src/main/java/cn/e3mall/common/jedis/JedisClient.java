package cn.e3mall.common.jedis;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public interface JedisClient {

    JedisCluster getJedisCluster();

    void setJedisCluster(JedisCluster jedisCluster);

    void setJedisPool(JedisPool jedisPool);

    JedisPool getJedisPool();

    JedisPool setJedisPool();

    String set(String key, String value);
	String get(String key);
	Boolean exists(String key);
	Long expire(String key, int seconds);
	Long ttl(String key);
	Long incr(String key);
	Long hset(String key, String field, String value);
	String hget(String key, String field);
	Long hdel(String key, String... field);
}
