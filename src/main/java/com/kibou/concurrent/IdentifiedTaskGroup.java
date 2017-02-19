package com.kibou.concurrent;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kibou.exception.TaskInvokeException;
import com.kibou.tuple.Pair;

/**
 * 任务组 -- 将多个任务归组并可对其并发执行 
 * @author shijun.shi@meicloud.com
 *
 * @see {@link AsyncQuery}
 * @param <K>
 * @param <T>
 */
public class IdentifiedTaskGroup<K,T> extends TaskGroup<T>{
	
	private final List<IdentifiedTask<K,T>> identifiedTasks;
	
	IdentifiedTaskGroup(Builder<K,T> builder){
		super(builder.groupName, 
				builder.timeout,
				builder.timeunit,
				builder.trace, 
				builder.executor == null ? 	
						AsyncQuery.getExecutorService() : builder.executor);
		this.identifiedTasks = builder.identifiedTasks;
	}
	
	public Map<K, T> collectIntoMap(){
		List<Pair<K, T>> invokeAndWait = invokeAndWait();
		Map<K,T> ktMap = Maps.newHashMapWithExpectedSize(invokeAndWait.size());
		for(Pair<K,T> pair : invokeAndWait){
			ktMap.put(pair.getFirst(), pair.getSecond());
		}
		return ktMap;
	}
	
	/*public List<Pair<K,T>> collectInto(Map<K,Object> accumulator){
		List<Pair<K, T>> invokeAndWait = invokeAndWait();
		MapUtil.copyPairsIntoMap(accumulator, invokeAndWait);
		return invokeAndWait;
	}*/
	
	public List<IdentifiedTask<K,T>> getTask(){
		return Collections.unmodifiableList(identifiedTasks);
	}
	
	public List<Pair<K,T>> invokeAndWait(){
		
		if(CollectionUtils.isEmpty(identifiedTasks))
			return Collections.emptyList();
		
		List<Pair<K,T>> results = 
				Lists.newArrayListWithExpectedSize(identifiedTasks.size());
		
		List<IdentifiedFuture<K, T>> futures =
				Lists.newArrayListWithExpectedSize(identifiedTasks.size());
		
		for(IdentifiedTask<K, T> task : identifiedTasks){
			IdentifiedFuture<K, T> future = new IdentifiedFuture<>(task);
			getExecutorService().execute(future);
			futures.add(future);
		}
		
		
		int taskLeft = 0;
		Throwable cause = null; 
		
		for(IdentifiedFuture<K, T> future : futures){
			
			if(cause != null){
				taskLeft = future.cancel(true) ? taskLeft + 1 : taskLeft;
				continue;
			}
			
			try {
				T ret = hasTimeout() ? future.get(getTimeout(), getTimeunit()) :  future.get();
				results.add(Pair.of(future.getKey(),ret));
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				cause = e;
			}
			
		}
		
		if(cause != null){
			if(trace != null){//FIXME(shisj) combine with cause!;
				StackTraceElement stackTraceElement = trace.getStackTrace()[0];
				System.err.println(stackTraceElement);
			}
			throw new TaskInvokeException("TaskGroup : " + getGroupName() + " got a exception!," + 
					taskLeft + " task left uncancelled/running",cause);
		}
		
		return results;
	}
	
	static class IdentifiedFuture<K,V> extends FutureTask<V>{
		private final K key;
		
		public IdentifiedFuture(IdentifiedTask<K,V> idTask) {
			super(idTask);
			this.key = idTask.getKey();
		}
		
		public IdentifiedFuture(K key,Callable<V> callable) {
			super(callable);
			this.key = key;
		}
		public IdentifiedFuture(K key,Runnable runnable,V result) {
			super(runnable,result);
			this.key = key;
		}
		
		public K getKey() {
			return key;
		}
	}
	
	public static class Builder<K,T> {
		
		private List<IdentifiedTask<K,T>> identifiedTasks;
		
		private final String groupName;
		
		private long timeout;
		private TimeUnit timeunit;
		
		private Exception trace;
		
		private ExecutorService executor;
		
		public Builder(String groupName) {
			this(groupName,5);
		}
		
		public Builder(String groupName,int estimatedSize) {
			Preconditions.checkArgument(estimatedSize > 0,"size must greater than 0");
			this.identifiedTasks = Lists.newArrayListWithExpectedSize(estimatedSize);
			this.groupName = groupName;
		}
		
		public Builder<K,T> addTask(K key,Runnable task) {
			return addTask(key,task,null);
		}
		
		public Builder<K,T> addTask(K key,Runnable task,T result) {
			return addTask(key,Executors.callable(Preconditions.checkNotNull(task),result));
		}
		
		public  Builder<K,T> addTask(K key,Callable<T> task) {
			identifiedTasks.add(Tasks.identifiedTask(key, task));
			return this;
		}
		
		public Builder<K,T> timeout(long timeout){
			return timeout(timeout,TimeUnit.MILLISECONDS);
		}
		
		public Builder<K,T> timeout(long timeout,TimeUnit timeunit){
			this.timeout = timeout;
			this.timeunit = timeunit;
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public  Builder<K,T> addTasks(K key,Callable<T>... tasks) {
			for(Callable<T> task : tasks){
				identifiedTasks.add(Tasks.identifiedTask(key, task));
			}
			return this;
		}
		
		public Builder<K,T> trace(Exception trace){
			this.trace = trace;
			return this;
		}
		public Builder<K,T> executor(ExecutorService executor){
			this.executor = executor;
			return this;
		}
		
		public IdentifiedTaskGroup<K,T> build(){
			return new IdentifiedTaskGroup<K,T>(this);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		final CountDownLatch countDownLatch = new CountDownLatch(2);
		
		List<Pair<String, String>> invokeAndWait = 
			new Builder<String,String>("taskgroup2", 3)
				.addTask("1",new Callable<String>() {
					public String call() throws InterruptedException {
						Thread.sleep(600);
						countDownLatch.countDown();
						return Thread.currentThread().getName();
					}
				})
				.addTask("2",new Callable<String>() {
					public String call() {
						countDownLatch.countDown();
						return Thread.currentThread().getName();
					}
				})
				.addTask("3",new Callable<String>() {//uncancellable task
					public String call() {
						JUCHelper.waitUnInterruptibly(700);
						countDownLatch.countDown();
						return Thread.currentThread().getName();
					}
				})
			.timeout(500)
//			.executor(Executors.newFixedThreadPool(10,new NamedThreadFactory("IdTaskThread", true)))
			.build()
		.invokeAndWait();
		
		System.out.println(invokeAndWait);
		
		countDownLatch.await();
	}
}