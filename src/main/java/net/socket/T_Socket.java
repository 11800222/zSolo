package net.socket;

import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class T_Socket {
	public static CountDownLatch countDownLatch = new CountDownLatch(2);

	public static void main(String[] args) throws Exception {
		Socket client = connect(23333);
		Scanner sc = new Scanner(System.in);
		String option = sc.nextLine();
		switch (option) {
		case "1": {
			client.close();
			break;
		}
		case "2": {
			client.shutdownOutput();
			break;
		}
		}
		countDownLatch.await();
	}

	public static Socket connect(int port) throws Exception {
		Socket client = new Socket("127.0.0.1", port);

		System.out.println("Socket in port : " + client.getLocalPort() + " has connected to ");

		return client;
	}
}
