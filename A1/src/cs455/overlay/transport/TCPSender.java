package cs455.overlay.transport;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

import cs455.overlay.wireformats.Protocol;

/**
 * 
 * @author Cooper Scott
 * CS455 - Overlay
 * cs455.overlay.transport.TCPSender.java
 * Object that handles the sending of messages after a connection has been made
 *
 */

public class TCPSender implements Runnable{
	
	private Socket socket;
	private DataOutputStream out;
	public LinkedList<Protocol> queue;
	
	public TCPSender(Socket s) throws IOException {
		this.socket = s;
		out = new DataOutputStream(socket.getOutputStream());
		queue = new LinkedList<Protocol>();
	}
		
	public void sendData(byte[] dataToSend) throws IOException {
		int dataLength = dataToSend.length;
		out.writeInt(dataLength);
		out.write(dataToSend, 0, dataLength);
		out.flush();
	}
	public void run() {
		try {
			while(true){
				synchronized(queue){
					if(queue.isEmpty())
						queue.wait();
					Protocol p = takeFromQueue();
					if(p != null)
						sendData(p.getBytes());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized void addToQueue(Protocol p){
		queue.add(p);
	}
	
	private synchronized Protocol takeFromQueue(){
		if(!queue.isEmpty())
			return queue.removeFirst();
		return null;
	}

}
