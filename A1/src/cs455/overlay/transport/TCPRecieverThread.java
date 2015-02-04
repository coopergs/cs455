package cs455.overlay.transport;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * 
 * @author Cooper Scott
 * CS455 - Overlay
 * cs455.overlay.transport.TCPRecieverThread.java
 * Thread class that is a part of a TCPConnection. The thread listens for incoming messages
 *
 */

public class TCPRecieverThread implements Runnable{
	
	private Socket socket;
	private DataInputStream in;
	
	public TCPRecieverThread(Socket s) throws IOException {
		this.socket = s;
		in = new DataInputStream(socket.getInputStream());
	}
	
	
	public void run() {
		int dataLength;
		while (socket != null){
			try {
				dataLength = in.readInt();
				byte[] data = new byte[dataLength];
				in.readFully(data, 0, dataLength);
				System.out.println("Recieved Stuff");	//TODO send to main thread
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
