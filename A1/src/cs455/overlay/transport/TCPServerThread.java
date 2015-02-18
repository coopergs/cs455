package cs455.overlay.transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import cs455.overlay.node.Manager;

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
	private TCPConnectionsCache cache;
	private Manager node;
	
	public TCPServerThread(int portnum, Manager n) throws Exception{
		try {
			this.port = portnum;
			this.node = n;
			server = new ServerSocket(port);
			port = server.getLocalPort();
			cache = new TCPConnectionsCache();
		} catch (Exception e) {
			throw new Exception("Could not listen on port " + port);
		}
	}
	
	public void run() {
		System.out.println("Listening for connections on port " + port);
		while(!Thread.interrupted()){
			try {
				server.setSoTimeout(500);
			} catch (SocketException e) {
				System.err.println("Error with the thread.");
			}
			try {
				Socket s = server.accept();
				cache.add(new TCPConnection(s, node));
			} catch (IOException e) {}
		}
		System.out.println("Server thread terminated");
	}
	
	public int getPort(){
		return port;
	}

}
