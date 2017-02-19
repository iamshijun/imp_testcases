package com.kibou.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

public final class Tasks {

	public static <K,R> IdentifiedTask<K, R> identifiedTask(K key,Callable<R> delegate){
		return new DelegateIdentifiedTask<K, R>(key, delegate);
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		
		final CountDownLatch cdl = new CountDownLatch(1);
		
		Executors.newSingleThreadExecutor()
			.submit(new Runnable(){
				@Override
				public void run() {
					
					
				}
			});
		
		
		cdl.await();
	}
}
