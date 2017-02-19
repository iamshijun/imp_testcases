package com.kibou.concurrent;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncQueryFuture<T> implements Future<T> {

	private final Future<T> delegate;
	private final Map<String, Object> params;

	public AsyncQueryFuture(Future<T> delegate, Map<String, Object> params) {
		super();
		this.delegate = delegate;
		this.params = params;
	}

	public Map<String, Object> getQueryParams() {
		return params;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return delegate.cancel(mayInterruptIfRunning);
	}

	@Override
	public boolean isCancelled() {
		return delegate.isCancelled();
	}

	@Override
	public boolean isDone() {
		return delegate.isDone();
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
		return delegate.get();
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return delegate.get(timeout, unit);
	}

}