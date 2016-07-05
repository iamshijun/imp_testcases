package com.kibou.test;

import org.junit.Test;

public class ContextClassLoaderTest {
	@Test
	public void testClassLoaders() throws Exception{
		//AppClassLoader
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
		System.out.println(systemClassLoader);
		
		//AppClassLoader
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		System.out.println(contextClassLoader);
		
		Thread myThread = new MyThread();
		ClassLoader emptyCl = new ClassLoader() {
			@Override
			public String toString() {
				return "emptyCl";
		}};
		myThread.setContextClassLoader(emptyCl);
		
		myThread.start();
		myThread.join();
	}
	
	class MyThread extends Thread{
		@Override
		public void run() {
			Thread innerThread = new Thread(); //parent is MyThread
			System.out.println(innerThread.getContextClassLoader());//默认会使用父线程(常见innerThread线程的所在线程 的contextClassLoader)
		}
	}
}
