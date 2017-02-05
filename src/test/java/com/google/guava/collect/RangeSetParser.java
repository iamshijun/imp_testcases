package com.google.guava.collect;

import static com.google.common.base.CharMatcher.is;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;


public abstract class RangeSetParser {

	private static CharMatcher charMatcher =  is('[').or(is(']')).or(is('(')).or(is(')'));
	
	public static RangeSet<Integer> range(String input){
		RangeSet<Integer> rangeSet = TreeRangeSet.create();
		for(String slice : Splitter.on(",").split(input)){
			
			if(charMatcher.matches(slice.charAt(0)) ||
					charMatcher.matches(slice.charAt(slice.length()-1))){
				
				String numRange = slice.replaceAll("\\]|\\[|\\)|\\(", "");
				String[] nums = numRange.split("-");//can be "‥"/","/".." any chars which not represent the number 
				int num1 = Integer.parseInt(nums[0]);
				int num2 = Integer.parseInt(nums[1]);
				
				if(slice.startsWith("[") && slice.endsWith("]")){
					rangeSet.add(Range.closed(num1, num2));
				}else if(slice.startsWith("(") && slice.endsWith(")")){
					rangeSet.add(Range.open(num1, num2));
				}else if(slice.startsWith("[") && slice.endsWith(")")){
					rangeSet.add(Range.closedOpen(num1, num2));
				}else if(slice.startsWith("(") && slice.endsWith("]")){
					rangeSet.add(Range.openClosed(num1, num2));
				}
			}else{
				rangeSet.add(Range.singleton(Integer.valueOf(slice)));
			}
		}
		
		return rangeSet;
	}
	
	
	@Test
	public void testParse(){
		
		RangeSet<Integer> range = range("[1-10],11,(13-20),[30-42),56,59,(63-70]");//,4,63
		System.out.println(range);
		
		Assert.assertFalse(range.contains(55));
		Assert.assertFalse(range.contains(13));
		Assert.assertTrue(range.contains(66));
	}
}
