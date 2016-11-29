package com.google.guava.collect;

import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import com.kibou.collect.TIterables;

import ch.qos.logback.core.net.SyslogOutputStream;

//https://github.com/google/guava/wiki/NewCollectionTypesExplained#rangeset
public class RangeSetTest {

	@Test
	public void testRangeSet(){
		RangeSet<Integer> rangeSet = TreeRangeSet.create();
		rangeSet.add(Range.closed(1, 10)); // {[1, 10]}
		System.out.println(rangeSet);
		System.out.println(rangeSet.complement()); //rangeSet的补集.(-infinity,1),(10,+infinity)
		
		rangeSet.add(Range.closedOpen(11, 15)); // disconnected range: {[1, 10], [11, 15)}
		System.out.println(rangeSet);
		
		rangeSet.add(Range.closedOpen(15, 20)); // connected range; {[1, 10], [11, 20)}
		System.out.println(rangeSet);
		
		rangeSet.add(Range.openClosed(0, 0)); // empty range; {[1, 10], [11, 20)}
		System.out.println(rangeSet);
		
		rangeSet.remove(Range.open(5, 10)); // splits [1, 10]; {[1, 5], [10, 10], [11, 20)}
		System.out.println("After Remove :" + Range.open(5, 10));
		System.out.println(rangeSet);
		
		//System.out.println(Range.atLeast(10));// [10‥+∞)
		rangeSet.add(Range.openClosed(23, 29));
		TIterables.toMultilineString(rangeSet.asRanges(), "RangeSet.asRanges : ",new TIterables.Formatter<Range<Integer>>(){
			@Override
			public String format(Range<Integer> t) {
				return "range: " + t.toString();
			}
		});
		
		System.out.println(rangeSet.contains(20));
		System.out.println(rangeSet.rangeContaining(5));//返回包含5的Range
		
		System.out.println(rangeSet.encloses(Range.open(13, 25)));
		
		Range<Integer> span = rangeSet.span();//返回包含rangeSet的最小Range
		System.out.println(span);
	}
}
