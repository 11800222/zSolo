package net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class T_ServerSocket {
	public static ArrayList<Socket> clients = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		openServer(23333);

	}

	public static void openServer(int port) throws IOException {
		ServerSocket ss = new ServerSocket(port);
		System.out.println("listen on  " + port);
		while (true) {
			final Socket client = ss.accept();
			clients.add(client);
			listenThisSocket(client);
		}
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
						if (b == -1)
							break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		t.start();
	}

}
