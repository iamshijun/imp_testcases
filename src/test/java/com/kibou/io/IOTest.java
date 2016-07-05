package com.kibou.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;


public class IOTest {
	public static void main(String[] args) {
		//虽然FileWriter本身是OutputstreamWriter的子类,可以传入Outpustream作为构造函数的参数,但是FileWriter并没有暴露 FileOutputStream 为入参的构造函数
		//而是由其他构造函数去创建 FileOutputStream并用此调用父类的构造方法,FileReader同.
//		FileWriter writer = new FileWriter(new FileOutputStream(""));
		BufferedReader bufferedFileReader = null;
		try {
			URL url = IOTest.class.getResource("IOTest.class");
			File file  = new File(url.toURI());
			
			
			bufferedFileReader = new BufferedReader(new FileReader(file));
			String readLine = bufferedFileReader.readLine();
			System.out.println(readLine);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(bufferedFileReader != null){
				try {
					bufferedFileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
