package com.google.guava.collect;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

public class BiMapTest {
	//https://github.com/google/guava/wiki/NewCollectionTypesExplained#bimap
	
	//Bidirectional Map 双向Map
	
	/* Implementations:
	 * 
	 * Key-Value Map Impl	Value-Key Map Impl	Corresponding BiMap
	 *	HashMap	HashMap		HashBiMap
	 *	ImmutableMap		ImmutableMap		ImmutableBiMap
	 *	EnumMap				EnumMap				EnumBiMap
	 *	EnumMap				HashMap				EnumHashBiMap
	*/
	
	/*
	 * The traditional way to map values back to keys is to maintain two
	 * separate maps and keep them both in sync, but this is bug-prone and can
	 * get extremely confusing when a value is already present in the map. 
	 * For example:
	 */
	public void traditional(){
		Map<String, Integer> nameToId = Maps.newHashMap();
		Map<Integer, String> idToName = Maps.newHashMap();

		nameToId.put("Bob", 42);
		idToName.put(42, "Bob");
		// what happens if "Bob" or 42 are already present?
		// weird bugs can arise if we forget to keep these in sync...
	}
	
	/*
	 A BiMap<K, V> is a Map<K, V> that
	 
		1. allows you to view the "inverse" BiMap<V, K> with inverse()
		2. ensures that values are unique, making values() a Set
		//第二点要求 比较严格的一个数据集合的形式 .
	 */
	
	@Test
	public void testHashBiMap(){
		HashBiMap<String, Integer> hashBiMap = HashBiMap.create();
		hashBiMap.put("shijun", 47);
		hashBiMap.put("shifeng", 77);
		try{
			hashBiMap.put("reina", 47);//根据上述的第二点要求 put加入的元素的值不能是之前就存在的
			//如果真的需要替换的话 使用 forcePut 
		}catch(IllegalArgumentException iae){
			//ignore
		}
		hashBiMap.forcePut("reina", 47);
		
		//////////////////////////////////
		
		
		System.out.println(hashBiMap);
		
		BiMap<Integer, String> inverseBiMap = hashBiMap.inverse();
		System.out.println(inverseBiMap);
		Assert.assertEquals("reina",inverseBiMap.get(47));
	}
}
