package com.kibou.socket.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
//		long readStart = -1;
		try {
			serverSocket = new ServerSocket(8888, 1024);

			while (!Thread.interrupted()) {
				Socket socket = serverSocket.accept();
				socket.setSoTimeout(5000);
				
				InputStream inputStream = socket.getInputStream();
				byte buffer[] = new byte[256];
				
//				readStart = System.nanoTime();
				int readBytes = inputStream.read(buffer);
				
				//inputStream.available();
				String readStr = new String(buffer,0,readBytes);
				
				System.out.println(readStr);
				
				if(readStr.equals("close")){
					socket.close();
					break;
				}else if(readStr.startsWith("greet")){
					Thread.sleep(5 * 1000);
					socket.getOutputStream().write(
							("[Re]" + readStr.replaceFirst("greet","")).getBytes());
//					socket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
//			long sec = TimeUnit.SECONDS.convert(System.nanoTime() - readStart,TimeUnit.NANOSECONDS);
//			System.out.println("ReadTimeout Time elipsed : " + sec + "/s");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			if(serverSocket !=null && !serverSocket.isClosed()){
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
