package com.kibou.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class MyClientSocket {
	
	public synchronized static void readFromSocket(Socket socket){
		//
		if(socket == null) return;
		try {
			byte buffer[] = new byte[256];
			InputStream inputStream = socket.getInputStream();
			
			int readBytes = inputStream.read(buffer);
			//inputStream.available();
			String readStr = new String(buffer,0,readBytes);
			
			System.out.println(readStr);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			InetAddress localHostInetAddress = InetAddress.getLocalHost();
			Socket socket = new Socket(localHostInetAddress,8888);
			
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write("greet We are family".getBytes());
			
			Thread t = new Thread(){
				@Override
				public void run() {
					try {
						Thread.sleep(1*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					long start = System.nanoTime();
					System.out.println("[NewThread] ready to invoke f()");
					readFromSocket(null);
					System.out.println("[NewThread] invoke f() time elipsed :" 
							+ TimeUnit.SECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS));
				}
			};
			t.start();
			
			readFromSocket(socket);
			socket.close();
			
			t.join();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
