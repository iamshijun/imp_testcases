package com.kibou.lang;

public final class ArrayUtil {
	
	public static boolean isNotEmpty(Object[] arr){
		return !isEmpty(arr);
	}

	public static boolean isEmpty(Object[] arr){
		return arr == null || arr.length == 0;
	}
}
