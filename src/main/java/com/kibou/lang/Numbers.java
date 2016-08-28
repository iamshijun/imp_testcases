package com.kibou.lang;

public class Numbers {
	public static String digit(byte val, int digit) {
		int hi = 1 << (digit * 4);
		return Integer.toHexString(hi | (val & (hi - 1))).substring(1);
	}

	public static String digit(long val, int digit) {
		int hi = 1 << (digit * 4);
		return Long.toHexString(hi | (val & (hi - 1))).substring(1);
	}
	
	public static int byteToInt(byte b){
		return b & 0xff;  
		//首先 byte 到int的类型提升是带符号的提升看下面两个输出可知
//		byte b = -128;
//		System.out.println(Integer.toBinaryString( b & 0xff)); //可以认为是不带符号的提升,将后8位直接放在int的后八位上 其他部分都是0
//		System.out.println(Integer.toBinaryString(-128));
	}
	
	
	public static void main(String[] args) {
		int i = -128;
		byte b = (byte) i;
		System.out.println(b);
	}
}
