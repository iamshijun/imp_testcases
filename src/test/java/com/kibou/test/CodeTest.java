package com.kibou.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

public class CodeTest {

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
	
	@Test
	public void testEncoding() throws UnsupportedEncodingException{
		String str = "热水器 家用 电 储水";
		byte[] barr = str.getBytes("gbk");
//		byte[] barr = str.getBytes("utf-8");
		System.out.println(Arrays.toString(barr));
		
		System.out.println(Hex.encodeHexString(barr));
		
		String encode = URLEncoder.encode(str, "gbk");
		System.out.println(encode);
	}
}
