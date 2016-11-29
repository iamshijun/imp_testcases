package com.google.guava.collect;

import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.kibou.collect.TIterables;

//Multiset Is Not A Map
public class MultisetTest {
	//https://github.com/google/guava/wiki/NewCollectionTypesExplained#multiset

	private Map<String,Integer> dataPrepare(Multiset<String> multiset){
		
		Map<String,Integer> raw = Maps.newHashMap();
		raw.put("shijun", 3);
		raw.put("reina", 3);
		raw.put("shifeng", 4);
		
		multiset.add("shijun");
		multiset.add("shijun",2);
		
		multiset.add("reina");
		multiset.add("reina");
		multiset.add("reina");
		
		multiset.add("shifeng",4);
		return raw;
	}
	
	//Multiset  (Collection , Iterable)
	@Test
	public void testHashMultiset(){
		Multiset<String> hashMultiset = HashMultiset.create();
		
		Map<String,Integer> raw = dataPrepare(hashMultiset);
		
		Assert.assertEquals(raw.get("shijun").intValue(), hashMultiset.count("shijun"));
		
		int all = 0;
		for(String key : raw.keySet()){
			all += raw.get(key);
		}
		Assert.assertThat(hashMultiset, Matchers.hasSize(all));
		
		TIterables.toString(hashMultiset);
		//////////////////
		Assert.assertThat(hashMultiset.entrySet(), Matchers.hasSize(3));
		for(Multiset.Entry<String> entry : hashMultiset.entrySet()){
			//Assert.assertNotNull(raw.get(entry.getElement()));
			Assert.assertEquals(entry.getCount(), raw.get(entry.getElement()).intValue());
		}
		
		Assert.assertThat(hashMultiset.elementSet(), Matchers.hasSize(3));
		/////////////////
		
		int oldCount = hashMultiset.count("shijun");
		hashMultiset.remove("shijun"); // == hashMultiset.remove("shijun",1)
		Assert.assertEquals(oldCount - 1, hashMultiset.count("shijun"));
	}
	
	@Test
	@Ignore
	public void testImmutableMultiset(){
//		ImmutableMultiset.builder().add(..);
	}
	
	/*
	 * HashMultiset
	 * TreeMultiset
	 * LinkedHashMultiset
	 * ConcurrentHashMultiset
	 * ImmutableMultiset 
	 */
}
