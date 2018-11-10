package net.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class T_DatagramSocket {

	public static void main(String[] args) throws Exception {
		DatagramSocket datagramSocket = new DatagramSocket(45555);

		datagramSocket.send(new DatagramPacket(new byte[10], 10));

	}

}
