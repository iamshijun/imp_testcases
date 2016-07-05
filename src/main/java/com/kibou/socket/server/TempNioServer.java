package com.kibou.socket.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TempNioServer implements Runnable{
	public static void main(String[] args) {
		new TempNioServer().run();
	}
	
	@Override
	public void run() {
		try {
			int backlog = 10;
			InetSocketAddress addr = new InetSocketAddress("localhost", 9090);
			
			ServerSocketChannel serverSocketChannel = null;
			System.out.println("Starting the NioServer.....");
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.bind(addr,backlog);
			serverSocketChannel.configureBlocking(true);
			
			SocketChannel socketChannel = serverSocketChannel.accept();
			System.out.println("client da");
			socketChannel.finishConnect();
			
			socketChannel.configureBlocking(false);
			ByteBuffer dst = ByteBuffer.allocate(100);
			int read = socketChannel.read(dst);
			System.out.println(read);
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
