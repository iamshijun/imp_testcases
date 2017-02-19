package com.kibou.concurrent;

import java.util.concurrent.Callable;

public interface IdentifiedTask<K,V> extends Callable<V> {

	K getKey();
}
