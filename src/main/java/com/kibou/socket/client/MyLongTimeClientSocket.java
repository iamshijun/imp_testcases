package com.kibou.socket.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class MyLongTimeClientSocket {

	public static void main(String[] args) {
		Socket socket = null;
		try {
			InetAddress localHostInetAddress = InetAddress.getLocalHost();
			socket = new Socket(localHostInetAddress, 8888);

			Thread.sleep(1000 * 10);

			socket.getOutputStream().write("quit".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
