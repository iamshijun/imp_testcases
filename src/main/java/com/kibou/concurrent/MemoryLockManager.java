package com.kibou.concurrent;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;

/**
 * 内存锁管理 当前只有 mutext, ReentrantReadWriteLock.
 * @author shijun.shi@meicloud.com
 *
 */
public abstract class MemoryLockManager {

	private static ConcurrentMap<Object, Object> mutextMap = Maps.newConcurrentMap();
	
	private static ConcurrentMap<Object, ReentrantReadWriteLock> readWriteLockMap = Maps.newConcurrentMap();
	
	private static <T> T getLock(Object key,Supplier<T> supplier,ConcurrentMap<Object,T> lockMap){
		Preconditions.checkNotNull(key,"key cannot be null");
		
		T lock = lockMap.get(key);
		if(lock == null){
			lockMap.putIfAbsent(key, supplier.get());
			lock = lockMap.get(key);
		}
		return lock;
	}
	
	public static Object mutex(Object key){
		return getLock(key, new Supplier<Object>(){
			public Object get() {
				return new Object();
			}
		}, mutextMap);
	}
	
	public static ReentrantReadWriteLock readWriteLock(Object key){
		return getLock(key, new Supplier<ReentrantReadWriteLock>(){
			public ReentrantReadWriteLock get() {
				return new ReentrantReadWriteLock();
			}
		}, readWriteLockMap);
	}
}
