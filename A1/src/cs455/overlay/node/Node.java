package cs455.overlay.node;

import cs455.overlay.transport.TCPConnection;

public class Node implements Comparable<Object>{
	
	private String ip;
	private int port;
	private int overlayID;
	public TCPConnection tcp;
	public String routingInfo;
	
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

	public int compareTo(Object arg0) {
		if(arg0 instanceof Node){
			Node othernode = (Node) arg0;
			return new Integer(overlayID).compareTo(new Integer(othernode.getID()));
		}
		return 0;
	}

}
