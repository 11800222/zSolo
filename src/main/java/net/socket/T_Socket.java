package net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class T_Socket {
	public static CountDownLatch countDownLatch = new CountDownLatch(2);
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		System.out.println("client:  ");
		//		Socket client = connect(23333);
		Socket client = connect(25555);
		PrintOptions(client);

		InputStream inbefore = client.getInputStream();
		listenThisSocket(client);
		while (true) {
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
			case "3": {
				OutputStream out = client.getOutputStream();
				String hello = new String("hello");
				out.write(hello.getBytes());
				//				out.flush();
				//				client.close();
				System.out.println("case 3");
				break;
			}
			case "4": {
				int bb = inbefore.read();
				System.out.println(bb);

				InputStream in = client.getInputStream();
				int b = in.read();
				System.out.println(b);
				System.out.println("case 4");
				break;
			}
			}
		}
	}

	public static void PrintOptions(Socket socket) throws Exception {
		OutputStream out = socket.getOutputStream();

	}

	public static Socket connect(int port) throws Exception {
		Socket client = new Socket("127.0.0.1", port);

		System.out.println("Socket in port : " + client.getLocalPort() + " has connected to ");

		return client;
	}

	public static void listenThisSocket(final Socket client) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					InputStream in = client.getInputStream();
					System.out.println("begin listen to client  " + client.getPort());
					while (true) {
						int b = in.read();
						System.out.println("has read byte : " + b + " from client " + client.getPort());
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
