package net.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class T_SocketChannel {

	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		System.out.println("client:  ");

		SocketChannel client = SocketChannel.open();

		client.connect(new InetSocketAddress("127.0.0.1", 24444));

		System.out.println("Socket in port : " + client.socket().getLocalPort() + " has connected to ");

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
				ByteBuffer writeBuffer = ByteBuffer.allocate(25);
				writeBuffer.put((byte) 1);

				writeBuffer.flip();//要转换为读状态，因为接下来要让socket读

				client.write(writeBuffer);

				System.out.println("case 3");
				break;
			}

			}
		}
	}
}
