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
		//��ȻFileWriter������OutputstreamWriter������,���Դ���Outpustream��Ϊ���캯���Ĳ���,����FileWriter��û�б�¶ FileOutputStream Ϊ��εĹ��캯��
		//�������������캯��ȥ���� FileOutputStream���ô˵��ø���Ĺ��췽��,FileReaderͬ.
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
