package com.google.guava.collect;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

//https://github.com/google/guava/wiki/NewCollectionTypesExplained#rangemap
public class RangeMapTest {

	@Test
	public void testRangeMap(){
		RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
		rangeMap.put(Range.closed(1, 10), "foo"); // {[1, 10] => "foo"}
		System.out.println(rangeMap);
		
		rangeMap.put(Range.open(3, 6), "bar"); // {[1, 3] => "foo", (3, 6) => "bar", [6, 10] => "foo"}
		System.out.println(rangeMap);
		
		rangeMap.put(Range.open(10, 20), "foo"); // {[1, 3] => "foo", (3, 6) => "bar", [6, 10] => "foo", (10, 20) => "foo"}
		System.out.println(rangeMap);
		
		rangeMap.remove(Range.closed(5, 11)); // {[1, 3] => "foo", (3, 5) => "bar", (11, 20) => "foo"}
		System.out.println(rangeMap);
		
		rangeMap.put(Range.closed(7, 15), "foobar");//override [11,15] then {[1‥3] => foo, (3‥5) => bar, [7‥15] => foobar, (15‥20) => foo}
		System.out.println(rangeMap);
		
		/*
		 * ~asMapOfRanges():       views the RangeMap as a Map<Range<K>, V>. This can be used, for example, !!to iterate over the RangeMap.!!
		 * ~subRangeMap(Range<K>): views the intersection of the RangeMap with the specified Range as a RangeMap.
		 *   This generalizes the traditional headMap, subMap, and tailMap operations.*/
		
		Map<Range<Integer>, String> asMapOfRanges = rangeMap.asMapOfRanges();
		System.out.println(asMapOfRanges);
		
	}
}
