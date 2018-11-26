package net.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class EpollServer {
	public static ServerSocketChannel serverSocketChannel;
	public static Selector selector;
	public static SelectionKey theFirstClient;

	public static void main(String[] args) throws Exception {
		serverSocketChannel = ServerSocketChannel.open();

		serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 25555));

		selector = Selector.open();

		//要把channel注册到，必须修改channel为非阻塞模式
		serverSocketChannel.configureBlocking(false);

		// 注册 channel，并且指定感兴趣的事件是 Accept
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		dealWith_Selectot(selector);

		System.out.println("began to select");
		while (true) {
			String option = sc.nextLine();
			try {
				switch (option) {//select期间：
				case "1": {//能否修改已注册key的interest set——可以
					SelectionKey selectionKey = theFirstClient;
					selectionKey.interestOps(SelectionKey.OP_WRITE);
					//				selectionKey.channel().register(selector, SelectionKey.OP_WRITE);
					System.out.println("case 1");
					break;
				}
				case "2": {//能否注册新的channel——不可以，publicKeys集合被同步
					ServerSocketChannel testChannel = ServerSocketChannel.open();

					testChannel.socket().bind(new InetSocketAddress("127.0.0.1", 25566));

					testChannel.configureBlocking(false);

					SelectionKey selectionKey = testChannel.register(selector, SelectionKey.OP_ACCEPT);
					System.out.println("case 2");
					break;
				}
				case "3": {//能否增删改keys集合，selection期间可以remove  selectedKeys
					Set<SelectionKey> publicKeys = selector.keys();
					System.out.println(publicKeys.remove(serverSocketChannel)); //不可以

					Set<SelectionKey> selectedKeys = selector.selectedKeys();
					Iterator<SelectionKey> it = selectedKeys.iterator();
					SelectionKey key = it.next();
					it.remove();

					System.out.println("case 3");
					break;
				}
				case "4": {//看下selectedKeys集合 
					Set<SelectionKey> selectedKeys = selector.selectedKeys();
					System.out.println("case 4");
					break;
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public static void dealWith_Selectot(final Selector selector) throws Exception {
		new Thread(new Runnable() {
			public void run() {

				try {
					while (true) {

						selector.select();

						Set<SelectionKey> keys = selector.selectedKeys();

						dealWith_SelectedKeys(keys);

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	public static void dealWith_SelectedKeys(Set<SelectionKey> keys) throws Exception {
		Iterator<SelectionKey> it = keys.iterator();
		while (it.hasNext()) {
			SelectionKey key = it.next();

			it.remove();

			if (key.isAcceptable()) {//新连接的客户，关注其可读
				SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();

				socketChannel.configureBlocking(false);
				System.out.println("channel  " + socketChannel + " has connect!");

				theFirstClient = socketChannel.register(selector, SelectionKey.OP_READ);//关注其可读

			} else if (key.isReadable()) {//有客户数据可读
				SocketChannel socketChannel = (SocketChannel) key.channel();
				readBuff.clear();
				socketChannel.read(readBuff);

				readBuff.flip();
				System.out.println("channel " + socketChannel + "has read : " + new String(readBuff.array()));

				//				socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);//关注其可写 
			}
		}
	}

	public static Scanner sc = new Scanner(System.in);
	public static ByteBuffer readBuff = ByteBuffer.allocate(1024);
}
