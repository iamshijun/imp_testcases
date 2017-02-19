package com.kibou.concurrent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.kibou.exception.TaskInvokeException;

public abstract class TaskGroup<T> {
	
	private final String groupName;
	
	private long timeout;
	
	private TimeUnit timeunit;
	
	final Exception trace; 
	
	private final ExecutorService executorService;
	
//	private volatile int state;
//	
//	private static final int INITIAL = 0;
//	private static final int SUCCESS = 1;
//	private static final int FAIL = 2;
	//interrupted , cancelled , ex
	
	public TaskGroup(String groupName,ExecutorService executorService){
		this(groupName,0,TimeUnit.MILLISECONDS,new Exception(groupName + " trace exception"),executorService);
	}
	
	public TaskGroup(String groupName,long timeout,TimeUnit timeunit,Exception trace,ExecutorService executorService){
		this.groupName = groupName;
		this.timeout = timeout;
		this.timeunit = timeunit;
		this.trace = trace;
		this.executorService = Preconditions.checkNotNull(executorService);
	}
	
	public String getGroupName(){
		return groupName;
	}
	
	public long getTimeout() {
		return timeout;
	}
	
	public TimeUnit getTimeunit() {
		return timeunit;
	}
	
	public boolean hasTimeout(){
		return timeout > 0;
	}
	
	public ExecutorService getExecutorService(){
		//return Executors.unconfigurableExecutorService(executorService);
		return executorService;
	}	
	
	public List<Future<T>> invokeAll() throws InterruptedException{
		
		List<? extends Callable<T>> tasks = getTask();
		
		if(CollectionUtils.isEmpty(tasks))
			return Collections.emptyList();
		
		if(hasTimeout()){
			return executorService.invokeAll(tasks, timeout, timeunit);//timeout为所有任务总执行超时时间
		}else{
			return executorService.invokeAll(tasks);
		}
	}
	
	public List<T> invokeAndGetAll(){
		
		Throwable cause = null; 
		
		List<T> results = Collections.emptyList();
		
		List<Future<T>> futures = null;
		
		try {
			futures = invokeAll();
		} catch (InterruptedException ie) {
			cause = ie;
		}
		
		if(cause == null){
			if(CollectionUtils.isEmpty(futures))
				return Collections.emptyList();
			
			results = Lists.newArrayListWithExpectedSize(futures.size());
			
			for(Future<T> future : futures){
				try {
					results.add(future.get()); //TimeoutException => CancellationException
				} catch (InterruptedException ie) {
					ie.printStackTrace();//never happen!?
				} catch(ExecutionException | CancellationException e){
					cause = e;
				}
			}
		}
		
		if(cause != null){
			if(trace != null){//FIXME(shisj) combine with cause!;
				StackTraceElement stackTraceElement = trace.getStackTrace()[0];
				System.err.println(stackTraceElement);
			}
			throw new TaskInvokeException("TaskGroup : " + groupName + " got a exception!",cause);
		}
		
		return results;
	}
	
	public abstract List<? extends Callable<T>> getTask();
	
}