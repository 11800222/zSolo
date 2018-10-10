package net.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class T_ServerSocket {
	public static ArrayList<Socket> clients = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		openServer(18899);

	}

	public static void openServer(int port) throws IOException {
		ServerSocket ss = new ServerSocket(port);
		System.out.println("listen on 18899");
		while (true) {
			Socket client = ss.accept();
			clients.add(client);
			System.out.println("a client has connected to server");
		}
	}

}
