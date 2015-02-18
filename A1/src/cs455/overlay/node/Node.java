package cs455.overlay.node;

import java.io.IOException;

import cs455.overlay.transport.TCPSender;
import cs455.overlay.wireformats.Protocol;

public class Node {
	
	private String ip;
	private int port;
	private int overlayID;
	private TCPSender tcp;
	
	public Node(String ip, int port, int id, TCPSender tcp){
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
	
	public void send(Protocol p){
		try {
			tcp.sendData(p.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
