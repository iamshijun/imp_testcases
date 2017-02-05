package com.google.guava.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.util.concurrent.RateLimiter;
import com.google.common.util.concurrent.Uninterruptibles;

public class RateLimiterTest {

	//SmoothBursty
	@Test //平滑突发限流
	public void testRateLimiterSmooth(){//Smooth
		//	1、RateLimiter.create(5) 表示桶容量为5且每秒新增5个令牌，即每隔200毫秒新增一个令牌；
		//	2、limiter.acquire()表示消费一个令牌，如果当前桶中有足够令牌则成功（返回值为0），如果桶中没有令牌则暂停一段时间，比如发令牌间隔是200毫秒，则等待200毫秒后再去消费令牌（如上测试用例返回的为0.198239，差不多等待了200毫秒桶中才有令牌可用），这种实现将突发请求速率平均为了固定请求速率。
		RateLimiter limiter = RateLimiter.create(5);
		System.out.println(limiter.acquire());//acquire返回等待时间
		System.out.println(limiter.acquire());
		System.out.println(limiter.acquire());
		System.out.println(limiter.acquire());
		System.out.println(limiter.acquire());
		System.out.println(limiter.acquire());
		/*类似返回
		 * 0.0
		 * 0.196907
		 * 0.196894
		 * 0.199941
		 * 0.199916
		 * 0.199893
		 * */
	}
	
	//突发实例
	@Test
	public void testRateLimiterBursty(){
		RateLimiter limiter = RateLimiter.create(5);
		System.out.println(limiter.acquire(5)); //acquire 大于5的permit也能通过,只是后续的等待时间会随着增大
		System.out.println(limiter.acquire(1));
		System.out.println(limiter.acquire(1));
		//类似返回
		/*
		 * 0.0
		 * 0.99789
		 * 0.197628
		 * 
		 * limiter.acquire(5)表示桶的容量为5且每秒新增5个令牌，令牌桶算法允许一定程度的突发，所以可以一次性消费5个令牌，
		 * 但接下来的limiter.acquire(1)将等待差不多1秒桶中才能有令牌，且接下来的请求也整形为固定速率了。
		 */
	}
	
	@Test
	public void testRateLimiterBursty2(){//
		RateLimiter limiter = RateLimiter.create(5);
		//允许特发指的是可预支令牌permit!?
		System.out.println(limiter.acquire(7)); // 这里立即返回 并 预支了两个permit,后续请求多等待 400ms左右(两个令牌的进入时间)
		System.out.println(limiter.acquire(3));//达到此处此效果同上
		System.out.println(limiter.acquire(1));
	}
	
	@Test
	public void testRateLimiterBursty3(){//
		RateLimiter limiter = RateLimiter.create(2);
		System.out.println(limiter.acquire());
		
		Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
		System.out.println(limiter.acquire()); 
		System.out.println(limiter.acquire()); 
		System.out.println(limiter.acquire());
		//恢复正常
		System.out.println(limiter.acquire());
		System.out.println(limiter.acquire());
	}
	
	
	@Test
	public void testBursty() throws InterruptedException{
		RateLimiter rateLimiter = RateLimiter.create(5);
		CountDownLatch latch = new CountDownLatch(1);
		
		ExecutorService executor = Executors.newCachedThreadPool();
		int task = 5;
		while(task-- > 0){
			executor.execute(() -> {
				Uninterruptibles.awaitUninterruptibly(latch);
				System.out.println(Thread.currentThread().getName() + " : " + rateLimiter.acquire(1));
			});
		}
		
		latch.countDown();
		
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);
	}
}
