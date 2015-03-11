package cs455.harvester.transport;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import cs455.harvester.wireformats.Decoder;

public class Reciever extends Thread{
	
	private Socket socket;
	private DataInputStream in;
	
	public Reciever(Socket s){
		this.socket = s;
		try {
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Error setting up connections.");
		}
	}
	
	public void run(){
		int dataLength;
		while (socket != null){
			try {
				dataLength = in.readInt();
				byte[] data = new byte[dataLength];
				in.readFully(data, 0, dataLength);
				Decoder.getInstance().decodeAndAct(data);
				} catch (SocketException se) {
					System.out.println(se.getMessage());
					break;
				} catch (IOException ioe) {
					System.out.println(ioe.getMessage());
					break;
					}
			}
	}

}
