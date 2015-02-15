package cs455.overlay.transport;

import java.io.IOException;
import java.net.Socket;

import cs455.overlay.node.Node;

/**
 * 
 * @author Cooper Scott
 * CS455 - Overlay
 * cs455.overlay.transport.TCPConnection.java
 * Object to be created by the TCPServerThread or a Node that contains a sender object and a reciever thread
 *
 */

public class TCPConnection {
	
	private Socket socket;
	private Node node;
	private TCPSender sender;
	private Thread reciever;
	
	public TCPConnection(Socket s, Node n)throws IOException{
		socket = s;
		node = n;
		sender = new TCPSender(socket);
		reciever = new Thread(new TCPRecieverThread(socket, node));
		reciever.start();
	}
	
	public void sendData(byte[] dataToSend) throws IOException {
		sender.sendData(dataToSend);
	}
	
	public void closeConnection(){
		reciever.interrupt();
		try{
			reciever.join();
			socket.close();
		} catch (Exception e) {
			System.out.println("Error with closing of thread");
		}
	}

}
