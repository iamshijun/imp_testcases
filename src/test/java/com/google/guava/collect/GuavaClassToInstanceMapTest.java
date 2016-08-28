package com.google.guava.collect;

import org.junit.Test;

import com.google.common.collect.MutableClassToInstanceMap;

public class GuavaClassToInstanceMapTest {
	// https://github.com/google/guava/wiki/NewCollectionTypesExplained#classtoinstancemap

	@Test //Technically, ClassToInstanceMap<B> implements Map<Class<? extends B>, B>
	public void testClassToInstanceMap() {
		MutableClassToInstanceMap<Number> ctiMap = MutableClassToInstanceMap.create();
		
		ctiMap.putInstance(Integer.class, Integer.valueOf(1));
		ctiMap.putInstance(int.class, 2);
		ctiMap.putInstance(Double.TYPE, 9.0);
		
		Integer one = ctiMap.getInstance(Integer.class);
		System.out.println(one);
		
		int two = ctiMap.getInstance(int.class);
		System.out.println(two);
		
		System.out.println(ctiMap.getInstance(Double.class));
		System.out.println(ctiMap.getInstance(Double.TYPE));
	}
}
