
package cs455.overlay.transport;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import cs455.overlay.node.Manager;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;

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
	private Manager manager;
	private TCPConnection tcp;
	
	public TCPRecieverThread(Socket s, Manager n, TCPConnection t) throws IOException {
		this.socket = s;
		this.manager = n;
		in = new DataInputStream(socket.getInputStream());
		tcp = t;
	}
	
	
	public void run() {
		int dataLength;
		while (socket != null){
			try {
				dataLength = in.readInt();
				byte[] data = new byte[dataLength];
				in.readFully(data, 0, dataLength);
				Event e = EventFactory.getInstance().createEvent(data);
				manager.processEvent(e, tcp);
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
