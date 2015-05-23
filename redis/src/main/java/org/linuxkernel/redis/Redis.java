package org.linuxkernel.redis;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Redis implements Closeable {
	private static final Logger _LOG = Logger.getLogger(Redis.class);

	private static JedisPool pool = null;
	private Jedis _jedis;

	public Redis(final String host) {
		if (pool == null) {
			pool = new JedisPool(new JedisPoolConfig(), host, 6379, 2000);
		}
		_jedis = pool.getResource();
		_LOG.info("connect to redis successfully!");
	}

	public Redis(final String host, final String password) {
		this(host, 6379, password);
	}

	public Redis(final String host, final int port, final String password) {
		if (pool == null) {
			pool = new JedisPool(new JedisPoolConfig(), host, port, 2000);
		}
		_jedis = pool.getResource();
		_jedis.auth(password);
		_LOG.info("connect to redis successfully!");
	}

	public void close() throws IOException {
		if (_jedis != null)
			pool.returnResourceObject(_jedis);

	}

	public String getKey(String key) {
		String result = _jedis.get(key);
		return result == null ? "" : result;
	}

	public List<String> getKeys(String[] keys) {
		List<String> list = _jedis.mget(keys);

		return list;

	}

	public void set(final String key, final String value) {
		_jedis.set(key, value);
	}

	public void set2List(final String name, final String value) {
		_jedis.lpush(name, value);
	}

	public List<String> getList(final String name) {
		List<String> list = _jedis.lrange(name, 0, 5);
		return list;
	}

	public Set<String> getAllKeyNames() {
		Set<String> keys = _jedis.keys("*");
		return keys;
	}
}