package net.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class T_ServerSocket {
	public static ArrayList<Socket> clients = new ArrayList<>();
	public static CountDownLatch countDownLatch = new CountDownLatch(2);
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		System.out.println("Server:  ");
		openServer(23333);

	}

	public static void openServer(int port) throws IOException, InterruptedException {
		ServerSocket ss = new ServerSocket(port);
		System.out.println("listen on  " + port);
		while (true) {
			final Socket client = ss.accept();
			clients.add(client);
			listenThisSocket(client);
			listenThisSocket(client);
			countDownLatch.await();
			/*	String option = sc.nextLine();
				switch (option) {
				case "1": {
					client.close();
					System.out.println("case 1");
					break;
				}
				case "2": {
					client.shutdownOutput();
					System.out.println("case 2");
					break;
				}
			
				}*/
		}
	}

	public static void listenThisSocket(final Socket client) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println("begin listen to client  " + client.getPort());
					int count = 0;
					while (true) {
						int b = client.getInputStream().read();
						System.out.println("has read byte : " + b + " from client " + client.getPort());
						++count;
						if (b == -1) {
							System.out.println(count);
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
