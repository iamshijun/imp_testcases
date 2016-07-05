package com.kibou.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SignalTest {

	public static void main(String[] args) throws Throwable {
		final ReentrantLock rlock = new ReentrantLock();
		final Condition rc = rlock.newCondition();
		final CountDownLatch latch = new CountDownLatch(1);
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName() + " started ");
					rlock.lockInterruptibly();
					try{
						latch.await();
						System.out.println("await");
						rc.await();
					}finally{
						rlock.unlock();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " finished ");
			}
		};
		t.start();
		
		rlock.lock();
		try{
			System.out.println("signal");
			rc.signal();
			latch.countDown();
		}finally{
			rlock.unlock();
		}
		t.join();
	}
}
