package com.kibou.test;

public class StringTest {

	public void testStringConcat(){
		String s1 = "i" + " love you,retieeru";
		String s2 = "i" , s3 = " love" ,s4 = " you" ;
		String s5 = s2 + s3 + s4 + " reina";
		
		System.out.println(s1);
		System.out.println(s5);
	}
}
