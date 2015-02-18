package cs455.overlay.node;

import cs455.overlay.transport.TCPConnection;

public class Node {
	
	private String ip;
	private int port;
	private int overlayID;
	public TCPConnection tcp;
	
	public Node(String ip, int port, int id, TCPConnection tcp){
		this.ip = ip;
		this.port = port;
		this.overlayID = id;
		this.tcp = tcp;
	}
	
	public String getIP(){
		return ip;
	}
	
	public int getPort(){
		return port;
	}
	
	public int getID(){
		return overlayID;
	}

}
