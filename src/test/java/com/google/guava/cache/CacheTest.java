package com.google.guava.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

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
	
	@Test
	public void testRateLimiter() throws ExecutionException{
		LoadingCache<Long, AtomicLong> counter =
		        CacheBuilder.newBuilder()
		                .expireAfterWrite(2, TimeUnit.SECONDS)
		                .build(new CacheLoader<Long, AtomicLong>() {
		                    @Override
		                    public AtomicLong load(Long seconds) throws Exception {
		                        return new AtomicLong(0);
		                    }
		                });
		long limit = 1000;
		while(true) {
		    //得到当前秒
		    long currentSeconds = System.currentTimeMillis() / 1000;
		    if(counter.get(currentSeconds).incrementAndGet() > limit) {
		        System.out.println("限流了:" + currentSeconds);
		        continue;
		    }
		    //业务处理
		}
	}
}
