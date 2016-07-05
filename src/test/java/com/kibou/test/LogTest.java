package com.kibou.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogTest {

	public static void main(String[] args) {
		System.setProperty("org.apache.commons.logging.diagnostics.dest", "STDOUT");
		Log log = LogFactory.getLog("root");
		
		log.info("Hello");
		
		System.out.println(testReturnFinally());
	}
	
	@SuppressWarnings("finally")
	public static int testReturnFinally(){
		try{
			return getAndPrint(1);
		}finally{
			return getAndPrint(2);
		}
	}
	
	public static int getAndPrint(int i){
		System.out.println(i);
		return i;
	}
}
