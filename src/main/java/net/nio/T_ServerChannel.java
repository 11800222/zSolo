package net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class T_ServerChannel {

	public static void main(String[] args) throws Exception {
		ServerSocketChannel ssc = ServerSocketChannel.open();

		ssc.socket().bind(new InetSocketAddress("127.0.0.1", 24444));

		SocketChannel client = ssc.accept();

		listenThisSocket(client);

	}

	public static void listenThisSocket(final SocketChannel client) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println("begin listen to client  " + client.socket().getPort());

					ByteBuffer readBuffer = ByteBuffer.allocate(256);//buffer初始为  可写 状态

					while (true) {

						int b = client.read(readBuffer); //在readBuffer没有空间时并不阻塞。直接返回0

						client.write(new ByteBuffer[3]);

						readBuffer.flip();//转换为读状态

						while (readBuffer.hasRemaining()) {
							System.out.println("has read byte : " + readBuffer.get() + " from client " + client.socket().getPort());
						}

						readBuffer.clear();//清空并转换为写状态，循环利用

						if (b == -1) {
							break;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		t.start();
	}

}
