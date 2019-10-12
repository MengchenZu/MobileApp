package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
	public static void main(String[] args) throws Exception{		
		@SuppressWarnings("resource")
		DatagramSocket  socket= new DatagramSocket(8800);
		int count = 0;
		while(true){
			byte[] data = new byte[1024];
			DatagramPacket packet= new DatagramPacket(data, data.length);
			socket.receive(packet);
			UDPThread thread= new UDPThread(packet);			
			thread.start();		
			count++;
			System.out.println("number of clients: "+count);
		}
	}


}
