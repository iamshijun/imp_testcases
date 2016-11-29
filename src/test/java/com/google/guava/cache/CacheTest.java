package com.google.guava.cache;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

public class CacheTest {

	@Test
	public void testLoadingCache() {
		CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
			@Override
			public String load(String key) throws Exception {
				return null;
			}

		});
	}
}
