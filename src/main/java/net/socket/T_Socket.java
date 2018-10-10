package net.socket;

import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class T_Socket {
	public static CountDownLatch countDownLatch = new CountDownLatch(2);

	public static void main(String[] args) throws Exception {
		connect(18899);
	}

	public static Socket connect(int port) throws Exception {
		Socket client = new Socket("127.0.0.1", port);

		client.shutdownOutput();

		client.shutdownInput();

		countDownLatch.await();
		return client;
	}
}
