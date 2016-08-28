package com.google.guava.collect;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multiset;
import com.kibou.collect.TIterables;

//Multimap Is Not A Map
public class GuavaMultimapTest {
	//https://github.com/google/guava/wiki/NewCollectionTypesExplained#multimap
	
	//=>like Map<K,Collection<V>>
	/*
	 *  Implementation	       Keys behave like...	Values behave like..
		ArrayListMultimap	    HashMap	ArrayList
		HashMultimap	        HashMap	HashSet
		LinkedListMultimap *	LinkedHashMap*	       LinkedList*
		LinkedHashMultimap**	LinkedHashMap	       LinkedHashSet
		TreeMultimap	          TreeMap	           TreeSet
		ImmutableListMultimap	ImmutableMap	       ImmutableList
		ImmutableSetMultimap	ImmutableMap	       ImmutableSet
	 * */

	// a->1 a->2 a->4 b->3 c->5
	// a->[1,2,4] b->3 c->5
	private void dataPrepare(Multimap<String, Integer> multiMap){
		//a
		multiMap.put("a", 1);
		multiMap.put("a", 2);
		multiMap.put("a", 4);
		//b
		multiMap.put("b", 3);
		//c
		multiMap.put("c", 5);
	}
	
	//ListMultimap
	@Test
	public void testListMultimap(){
		ListMultimap<String, Integer> listMultiMap = 
				MultimapBuilder.hashKeys().linkedListValues().build();
		
		dataPrepare(listMultiMap);
		
		// * values
		TIterables.toString(listMultiMap.values(),"Multimap.values() : ");
		
		Assert.assertTrue(listMultiMap.containsKey("a"));
		Assert.assertTrue(listMultiMap.containsValue(1));

		Assert.assertEquals(listMultiMap.size(), 5);//a,a,a,b,c
		
		// * keySet() view the distinct keys in the Multimap as a set
		// 可以从方法名中 set 猜测返回的是一个唯一的key集合 所以这里的大小是3 不是5
		Set<String> keySet = listMultiMap.keySet(); // Multimap.keySet().size =!= Multimap.size()
		TIterables.toString(keySet,"Multimap.keySet() : ");
		Assert.assertThat(keySet, Matchers.hasSize(3));//
		
		//* keys() : Multiset , views the keys of the Multimap as a Multimap,with multiplicity equals
		// to the number of values associated to that key.
		Multiset<String> keys = listMultiMap.keys();
		TIterables.toString(keys,"Multimap.keys() : ");
		Assert.assertThat(keys, Matchers.hasSize(5));//a(3),b(1),c(1)
		
		////////////////////////////////////////////
		
		// Multimap.entries() return all entries for all keys in the Multimap
		// @see Multimap.keys() 另外注意和 keys() 一样都是返回所有 key/entry,
		Collection<Entry<String, Integer>> entries = listMultiMap.entries();
		TIterables.toString(entries,"Multimap.entries : ",new TIterables.Formatter<Entry<String, Integer>>() {
			public String format(Entry<String, Integer> next) {
				return next.getKey() + "->" + next.getValue();
			}
		});
		
		List<Integer> collection = listMultiMap.get("a");
		Assert.assertThat(collection, org.hamcrest.Matchers.equalTo(Arrays.asList(1,2,4)));

		
		//* Multimap.asMap
		//Multimap.entries vs Multimap.asMap.entrySet
		Map<String, Collection<Integer>> listMultiMapAsMap = listMultiMap.asMap();
		Assert.assertEquals(listMultiMapAsMap.size(), 3); // a,b,c

		Set<Entry<String, Collection<Integer>>> entrySet = listMultiMapAsMap.entrySet();
		TIterables.toString(entrySet,"Multimap.asMap.entrySet : ");
		
		try{
			listMultiMapAsMap.put("d", Lists.newArrayList(7,8,9));
//			listMultiMapAsMap.putAll(m);
			Assert.fail("View return by asMap is not support put/putAll operation,but remove and change");
		}catch(UnsupportedOperationException uoe){
			//ignore
		}
		
		////////////////////////////////
		
		listMultiMapAsMap.remove("a");
		Assert.assertThat(listMultiMap.get("a"), Matchers.empty());
		
		Collection<Integer> bValue = listMultiMapAsMap.get("b");
		bValue.add(33);
		
		Assert.assertEquals(bValue, listMultiMap.get("b"));
		//System.out.println(listMultiMap.get("b")); // [3,33]
	}
	
	//SetMultimap
	public void testSetMultimap(){
		
	}
}
