package com.kibou.j8.interf.impl;

import com.kibou.j8.interf.OneParameterNotReturn;

public class InputIntNoReturn extends OneParameterNotReturn<Integer> {

	@Override
	public void doWith(Integer i){
		System.out.println(i);
	}
}
