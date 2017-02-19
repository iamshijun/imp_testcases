package com.kibou.concurrent;

public interface TaskHandler<T> {

    public void onCompleted(T result);
  
    public void onError(Throwable e);  
}
