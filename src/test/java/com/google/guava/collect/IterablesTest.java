package com.google.guava.collect;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import com.google.common.primitives.Ints;

public class IterablesTest {

	@Test
	public void testSequentialIterator() {
		//实现AbstractSequentialIterator,当computeNext返回null时结束,并使用先前值生成当前值
		Iterator<Integer> powersOfTwo = new AbstractSequentialIterator<Integer>(1) {
			protected Integer computeNext(Integer previous) {
				return (previous == 1 << 30) ? null : previous * 2;
			}
		};
		for(int i = 0 ; i < 10 && powersOfTwo.hasNext();++i){
			System.out.println(powersOfTwo.next());
		}
	}

	@Test
	public void testPeekingIterator(){
		List<Integer> intList = Ints.asList(1,2,2,5,2,3,7,2,9,0,10,2);
		Collections.sort(intList);
		
		PeekingIterator<Integer> peekingIterator = Iterators.peekingIterator(intList.iterator());
		List<Integer> result = Lists.newArrayList();
		while(peekingIterator.hasNext()){
			Integer current = peekingIterator.next();
			//过滤掉重复的元素
			while(peekingIterator.hasNext() && peekingIterator.peek().equals(current)){
				peekingIterator.next();
			}
			result.add(current);
		}
		System.out.println(result);
	}
	
}
