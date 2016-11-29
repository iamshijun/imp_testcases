package com.kibou.exception;

import com.kibou.exception.FException;

public class ExceptionTest {

	public void f() throws FException{}
	
	public static void main(String[] args) {
		new ExceptionTest().f();
	}
}
