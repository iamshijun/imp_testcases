package com.kibou.concurrent;

public interface ActionHandler {

    public void onCompleted();
  
    public void onError(Throwable e);  
}
