package cs455.overlay.transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * 
 * @author Cooper Scott
 * CS455 - Overlay
 * cs455.overlay.transport.TCPServerThread.java
 * Thread class that listens for connections and spawns a new thread for each connection
 *
 */

public class TCPServerThread implements Runnable{
	
	private int port;
	private ServerSocket server;
	
	public TCPServerThread(int portnum) throws Exception{
		try {
			this.port = portnum;
			server = new ServerSocket(port);
		} catch (Exception e) {
			throw new Exception("Could not listen on port " + port);
		}
	}
	
	public void run() {
		System.out.println("Listening for connections on port " + port);
		while(!Thread.interrupted()){
			try {
				server.setSoTimeout(500);
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Socket s = server.accept();
				System.out.println("Connection Established");
			} catch (IOException e) {}
		}
	}

}
