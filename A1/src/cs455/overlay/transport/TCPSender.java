package cs455.overlay.transport;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 
 * @author Cooper Scott
 * CS455 - Overlay
 * cs455.overlay.transport.TCPSender.java
 * Object that handles the sending of messages after a connection has been made
 *
 */

public class TCPSender {
	
	private Socket socket;
	private DataOutputStream out;
	
	public TCPSender(Socket s) throws IOException {
		this.socket = s;
		out = new DataOutputStream(socket.getOutputStream());
	}
		
	public void sendData(byte[] dataToSend) throws IOException {
		int dataLength = dataToSend.length;
		out.writeInt(dataLength);
		out.write(dataToSend, 0, dataLength);
		out.flush();
	}

}
