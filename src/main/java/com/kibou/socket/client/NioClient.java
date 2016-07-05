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
		 * invoking the finishConnect method. XXX 如果是在非阻塞的情况下调用的connect后续必须调用finishConnect来完成连接
		 * 另外如果在未完成连接的情况调用finishConnect还是会返回false的,所以也注意调用的场合
		 */
		if(connected){
			System.out.println("Owa.... connected immediately");
		}//else 下面需要调用finisheConnect 才算是完成连接
		
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
					//(如果不执行该finishe语句,selector.select()即使没有任何事件每次都会返回0,可能是没有完成连接的socket)
					selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);//change ops
				}
				if(selectionKey.isWritable()){
					System.out.println("Writable");
					ByteBuffer byteBuffer = ByteBuffer.allocate(10);
					byteBuffer.put("hello".getBytes());
					byteBuffer.flip();
					socketChannel.write(byteBuffer);
					
					/*test 1 - 等待数秒后再注册读事件监听. 仍然能收到该事件的响应
					 * 应该是:虽然没有注册该事件但是也是会被selector捕捉到(系统反馈)但是不会在此处返回而已()
					 * 并且一直保留,直到在selector的存储中把其删除
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
					/*test 1 注释掉system.out后的代码(不处理掉channel中的数据),每次都会提示readable 知道处理完为止.
					 if(round++ > 3)
						break outer;*/
				}
			}
		}
		
		selector.close();
	}
}
