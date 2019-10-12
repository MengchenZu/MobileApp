package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPThread extends Thread {
	DatagramPacket packet = null;
	
	public UDPThread(DatagramPacket packet) {
		this.packet = packet;
	}

	public void run() {
		byte[] ans = new byte[packet.getLength()];
		
		System.arraycopy(packet.getData(), 0, ans, 0, packet.getLength());
		
		byte[] toClient="answer".getBytes();
		InetAddress address=packet.getAddress();
		
		int port=packet.getPort();
		DatagramPacket outPacket =new DatagramPacket(toClient, toClient.length,address,port);
		DatagramSocket socket;
		try {
			socket = new DatagramSocket();
			socket.send(outPacket);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
