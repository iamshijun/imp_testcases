package com.kibou.exception;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class ExceptionTest {

	public void f() throws FException{}
	
	public static void main(String[] args) {
		new ExceptionTest().f();
	}
	
	@Test
	public void testStackTrace(){
		Exception exception = new Exception();
		StackTraceElement[] stackTrace = exception.getStackTrace();
		for(StackTraceElement sse : stackTrace){
			System.out.println(sse);
		}
	}
	
	@Test
	public void testStackTraceAsync() throws InterruptedException{
		
		//biz1.
		//....
		Task task = new Task();
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Void> submit = executor.submit(task);
		
		try {
			submit.get();
		} catch (ExecutionException e) {
			e.printStackTrace();//在pint cause的时候java会将重复的stackTrace部分用 n more...代替,可解开下属的两个注释看结果
			//System.err.println("\n\n\n\n");
			//e.getCause().printStackTrace();
		}
		
		Thread.sleep(2000);
		executor.shutdownNow();
	}
	
	class Task implements Callable<Void>{

		Exception trace;
		public Task(){
			trace = new Exception("trace exception");
		}
		
		@Override
		public Void call() throws Exception {
			try{//biz2
				System.out.println(1 / 0);
			}catch(Exception e){
				//e.printStackTrace();
				//trace.printStackTrace();
				throw ExceptionUtils.replaceAsyncTraceWithInitiator(e, trace);
				//throw e;
			}
					
			return null;
		}
		
	}
}
