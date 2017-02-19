package com.kibou.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * @author shijun.shi@meicloud.com
 *
 */
public final class JUCHelper {
	
	//jul logger will be fine?
//	private static java.util.logging.Logger logger = Logger.getLogger("juc-util");

	private static long now(){
		return System.currentTimeMillis();
	}
	
	public static void waitUnInterruptibly(long timeout){
		waitUnInterruptibly(timeout, TimeUnit.MILLISECONDS);
	}
	
	public static void waitUnInterruptibly(long timeout,TimeUnit timeunit){
		long start = now(), expireAt = start + timeunit.toMillis(timeout),
			timeLeft = expireAt - start ;
		
		//try: LockSupport.parkUntil(expireAt);!!!
		do{
			try {
				Thread.sleep(timeLeft);
			} catch (InterruptedException ie) {
				//ignore ie
			}
			timeLeft = expireAt - now();
//			if(logger.isLoggable(Level.INFO) && timeLeft > 0)
//				logger.info(Thread.currentThread().getName() + " timeLeft=" + timeLeft + "/ms");
		}while(timeLeft > 0);
	}
	
	public static void main(String[] args) throws Exception {
		final CountDownLatch cdl = new CountDownLatch(1);
		
		ExecutorService executorService = 
				Executors.newSingleThreadExecutor(new NamedThreadFactory("JUC-test", true));
		/*
		Future<?> submit = 
			executorService.submit(new Runnable() {
				public void run() {
					waitUnInterruptibly(3, TimeUnit.SECONDS);
					cdl.countDown();
				}
			});
		
		Thread.sleep(1000);
		submit.cancel(true);
		System.out.println(cdl.getCount());
		
		cdl.await();
		System.out.println(cdl.getCount());*/
		
		
		List<Future<Void>> invokeAll = executorService.invokeAll(Arrays.asList(new Callable<Void>() {
			public Void call() {
				waitUnInterruptibly(30,TimeUnit.SECONDS);
				cdl.countDown();
				return null;
			}
		}),1,TimeUnit.SECONDS);
		
		Future<Void> future = invokeAll.get(0);
		Void void1 = future.get();
		System.out.println(void1);
	}
}
