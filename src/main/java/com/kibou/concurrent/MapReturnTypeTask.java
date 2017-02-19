package com.kibou.concurrent;

import java.util.Map;
import java.util.concurrent.Callable;

public interface MapReturnTypeTask<V> extends Callable<Map<String,V>>{

	public Map<String, V> call() throws Exception;

}
