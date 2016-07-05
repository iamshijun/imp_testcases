package com.kibou.test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.junit.Test;

public class TestCode {

	@Test
	public void testCode() throws UnsupportedEncodingException{
		String mykey = "foobar";
		byte[] bs = mykey.getBytes();
		System.out.println(Arrays.toString(bs));
		
		System.out.print(Integer.toBinaryString(bs[0] ));
		for(int i = 1;i < bs.length;++i){
			System.out.print(","+Integer.toBinaryString(bs[i]));
		}
		
		System.out.println();
		
		byte b = -128;
		System.out.println(Integer.toBinaryString( b & 0xff));
		System.out.println(Integer.toBinaryString(-128));
	}
	
	@Test
	public void testCode2() throws Exception{
		System.out.println(Arrays.toString("中".getBytes("iso-8859-1")));
		System.out.println(Arrays.toString("中".getBytes()));//platform's default charset
		System.out.println(Arrays.toString("中".getBytes("GBK")));
		System.out.println(Arrays.toString("中".getBytes("UTF-8")));
		System.out.println(Arrays.toString("中".getBytes("unicode")));
	}
}
