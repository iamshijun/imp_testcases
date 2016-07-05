package com.kibou.socket.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class NioServer implements Runnable{
	public static void main(String[] args) {
		new NioServer().run();
	}
	
	@Override
	public void run() {
		int backlog = 10;
		InetSocketAddress addr = new InetSocketAddress("localhost", 9090);
		
		ServerSocketChannel serverSocketChannel = null;
		try{
			System.out.println("Starting the NioServer.....");
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.bind(addr,backlog);
			serverSocketChannel.configureBlocking(true);
			
			final int pollerThreads = 1;
			Poller[] pollerThread = new Poller[pollerThreads];
			for(int i = 0; i < pollerThreads;++i){
				pollerThread[i] = new Poller();
				pollerThread[i].start();
			}
			// AtomicInteger pollerRotater = new AtomicInteger(0);
			int i = -1;
			while(true){
				SocketChannel socketChannel = serverSocketChannel.accept();
				System.out.println("client da");
				
				//socketChannle.finishConnect();
				setSocketOptions(socketChannel);
				
				i = (i + 1 >= pollerThreads ? 0 : i + 1);
				pollerThread[i++ % pollerThreads] //round-robin (tomcat Math.abs(pollerRotater.incrementAndGet) 当自增溢出时取正数)
						.register(new NioSocketChannel(socketChannel, SelectionKey.OP_READ));
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(serverSocketChannel != null){
				try {
					serverSocketChannel.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	class NioSocketChannel{
		int interOps;
		Socket socket;
		SocketChannel socketChannel;
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		public NioSocketChannel(SocketChannel socketChannel, int interOps) {
			super();
			this.interOps = interOps;
			this.socketChannel = socketChannel;
			this.socket = socketChannel.socket();
		}
	}

	private void setSocketOptions(SocketChannel socketChannle) throws IOException, SocketException {
		socketChannle.configureBlocking(false);
		//socketChannle.socket().setKeepAlive(true);
		//socketChannle.socket().setReceiveBufferSize(1024);
		//socketChannle.socket().setSoTimeout(0);//never timeout
	}
	
	class Poller extends Thread{
		boolean closed = false; 
		final Selector selector;
		
		private LinkedBlockingQueue<NioSocketChannel> pendingQueye = new LinkedBlockingQueue<NioServer.NioSocketChannel>();
		
		public Poller() throws IOException{
			 synchronized (Selector.class) {
	                // Selector.open() isn't thread safe
	                // http://bugs.sun.com/view_bug.do?bug_id=6427854
	                // Affects 1.6.0_29, fixed in 1.7.0_01
	                this.selector = Selector.open();
	                //this.selector = SelectorProvider.provider().openSelector();
	            }
		}
		
		public void register(NioSocketChannel nioSocketChannel/*,int timeout*/) throws ClosedChannelException{//or Wrap channel with higer level instance
			/*nioSocketChannel.socketChannel.register(selector, 
					nioSocketChannel.interOps, 
					nioSocketChannel);*/
			/*注意上述的直接register方式是不可行因为会被阻塞掉*/
			pendingQueye.add(nioSocketChannel);
			selector.wakeup();
		}
		
		public void close(){
			closed = true;
			selector.wakeup();
		}
		
		private boolean events() throws ClosedChannelException{
			int nums = 0;
			while(!pendingQueye.isEmpty()){
				NioSocketChannel nioSocketChannel = pendingQueye.poll();
				if(nioSocketChannel != null){
					nums++;
					nioSocketChannel.socketChannel.register(selector, 
							nioSocketChannel.interOps, 
							nioSocketChannel);
				}
			}
			return nums > 0;
		}
		
		public void run(){
			while(!closed){
				int readyChannels = 0;
				try {
					/*boolean hasEvent = */events();
					readyChannels = selector.select();
				}catch(IOException e){
					e.printStackTrace();
					return;
				}
				if(readyChannels > 0){
					Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
					while(iterator.hasNext()){
						SelectionKey selectionKey = iterator.next();
						iterator.remove();
						
						NioSocketChannel nioChannel = (NioSocketChannel) selectionKey.attachment();
						try{
							if(!selectionKey.isValid()){
								selectionKey.cancel();
								nioChannel.socketChannel.close();
								continue;
							}
							
							if(selectionKey.isReadable()){
								//dont do this here , we should put this into new ThreadPool (new context)
								int read = nioChannel.socketChannel.read(nioChannel.buffer);
								System.out.println(new String(nioChannel.buffer.array(),0,read));
								selectionKey.interestOps(SelectionKey.OP_WRITE);
								nioChannel.buffer.rewind();
							}
							
							if(selectionKey.isWritable()){
								if(nioChannel.buffer.remaining() > 0 ){
									nioChannel.socketChannel.write(nioChannel.buffer);
								} else {
									nioChannel.buffer.clear();
									nioChannel.socketChannel.close();
									selectionKey.cancel();
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
							try {
								nioChannel.socketChannel.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							selectionKey.cancel();
						}
					}
				}
			}
		}
	}
	//read io thread
	
	//write io thread
	
}
