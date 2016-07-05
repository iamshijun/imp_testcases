package com.kibou.socket.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		//Socket socket = new Socket("localhost", 9090);
		Selector selector = Selector.open();
		
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		
		socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		boolean connected = socketChannel.connect(new InetSocketAddress("localhost", 9090));
		/*
		 * If this channel is in non-blocking mode then an invocation of this
		 * method initiates a non-blocking connection operation. If the
		 * connection is established immediately, as can happen with a local
		 * connection, then this method returns true. Otherwise this method
		 * returns false and the connection operation must later be completed by
		 * invoking the finishConnect method. XXX ������ڷ�����������µ��õ�connect�����������finishConnect���������
		 * ���������δ������ӵ��������finishConnect���ǻ᷵��false��,����Ҳע����õĳ���
		 */
		if(connected){
			System.out.println("Owa.... connected immediately");
		}//else ������Ҫ����finisheConnect �������������
		
//		int round = 0;
		outer:
		for(;;){
			/*int readyChannels =*/ selector.select();
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			while(iterator.hasNext()){
				SelectionKey selectionKey = iterator.next();
				iterator.remove();
				
				System.out.println(selectionKey.interestOps());
				if(selectionKey.isConnectable()){
					System.out.println("isConnectable");
					socketChannel.finishConnect();//finishConnect is very important XXX
					//(�����ִ�и�finishe���,selector.select()��ʹû���κ��¼�ÿ�ζ��᷵��0,������û��������ӵ�socket)
					selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);//change ops
				}
				if(selectionKey.isWritable()){
					System.out.println("Writable");
					ByteBuffer byteBuffer = ByteBuffer.allocate(10);
					byteBuffer.put("hello".getBytes());
					byteBuffer.flip();
					socketChannel.write(byteBuffer);
					
					/*test 1 - �ȴ��������ע����¼�����. ��Ȼ���յ����¼�����Ӧ
					 * Ӧ����:��Ȼû��ע����¼�����Ҳ�ǻᱻselector��׽��(ϵͳ����)���ǲ����ڴ˴����ض���()
					 * ����һֱ����,ֱ����selector�Ĵ洢�а���ɾ��
					 * 
					 try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}*/
					selectionKey.interestOps(SelectionKey.OP_READ);
				}
				
				if(selectionKey.isReadable()){
					System.out.println("Readable");
					ByteBuffer dst = ByteBuffer.allocate(10);
					socketChannel.read(dst);
					System.out.println(dst.asCharBuffer().toString());
					
					selectionKey.cancel();
					break outer;
					/*test 1 ע�͵�system.out��Ĵ���(�������channel�е�����),ÿ�ζ�����ʾreadable ֪��������Ϊֹ.
					 if(round++ > 3)
						break outer;*/
				}
			}
		}
		
		selector.close();
	}
}
