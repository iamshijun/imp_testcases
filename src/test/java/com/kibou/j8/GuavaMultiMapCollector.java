package com.kibou.j8;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.kibou.tuple.Pair;

public class GuavaMultiMapCollector{

	public static void main(String[] args) {
		List<Pair<String,String>> pairList = Arrays.asList(
				Pair.of("shijun","1"),
				Pair.of("shijun","2"),
				Pair.of("shijun","3"),
				Pair.of("reina","4"),
				Pair.of("mimi","5")
			);
		
		HashMultimap<Object, Object> hashMultimap = 
				pairList.stream()
							.collect(HashMultimap::create,
									(mmap,pair) -> mmap.put(pair.getFirst(), pair.getSecond()),
									Multimap::putAll);
		
		System.out.println(hashMultimap);
	}
}
