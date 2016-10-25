package com.kibou.j8;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import com.kibou.j8.interf.impl.DummySort;
import com.kibou.j8.interf.impl.InputIntNoReturn;
import com.kibou.j8.interf.impl.InputIntReturnString;

/**
 *  Class::method  类方法<br>
 *  object::method  实例方法<br>
 *  在lambda表达是中的使用
 * @author SHISJ
 *
 */
public class LambdaMethodReferenceTest {

	public static void main(String[] args) {
		// T,U,R
		BiFunction<InputIntReturnString, Integer, String> biFunction = InputIntReturnString::doWith;
		//just like  : { (t,u) -> t.method(u)}
		InputIntReturnString inputIntReturnString = new InputIntReturnString();
		
		String apply = biFunction.apply(inputIntReturnString, 5);
		System.out.println(apply);
		
		/////////////////////////////////////////////////////////////
		//没有返回值
		//T,U
		BiConsumer<InputIntNoReturn,Integer> biConsumer = InputIntNoReturn::doWith;
		//just like : {(t,u) -> t.method(u) } 虽然看上去和上述一样但是编译器会根据情况 接口是否需要返回值.来生成对应的return语句.
		InputIntNoReturn inputIntNoReturn = new InputIntNoReturn();
		
		biConsumer.accept(inputIntNoReturn, 99);
		
		//从上述点可以看出,Stream中 collector(Supplier<R> suppler,BiConsumer<R,? super T> accumulator,BiConsumer<R,R> combiner) 的设计
		/*
		List<Integer> ilist = Arrays.asList(19,12,23,4,9,11,7);
		ilist.stream()
				.collect(ArrayList::new,ArrayList::add,ArrayList::addAll)
				.forEach(System.out::println);*/
		
		Consumer<Integer[]> sort = Arrays::sort; //staticMethod : void sort(String[]);
		// {t -> staticMethod(t)}
		
		DummySort dummySort = new DummySort();
		Consumer<Integer[]> sort2 = dummySort::sort;
		// {t -> method(t) }

		Integer[] arr = new Integer[]{19,12,23,4,9,11,7};
		sort.accept(arr);
		sort2.accept(arr);
	}
}
