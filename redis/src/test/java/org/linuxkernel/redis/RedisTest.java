package org.linuxkernel.redis;

import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class RedisTest {
	Redis redis = null;

	@Before
	public void initRedis() {
		redis = new Redis("192.168.186.128", 6379, "596702");
	}

	@Test
	public void getTest() {
		System.out.println(redis.getKey("key1"));
	}

	@Test
	public void getAllKeyTest() {
		Set<String> set = redis.getAllKeyNames();
		for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
			System.out.println(iter.next());
		}
	}
}
