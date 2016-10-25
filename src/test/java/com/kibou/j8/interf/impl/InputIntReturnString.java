package com.kibou.j8.interf.impl;

import com.kibou.j8.interf.OneParameterOneReturn;

public class InputIntReturnString extends OneParameterOneReturn<Integer, String> {

	@Override
	public String doWith(Integer i){
		return String.valueOf(i);
	}
}
