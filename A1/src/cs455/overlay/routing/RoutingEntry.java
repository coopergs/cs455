package cs455.overlay.routing;

import java.net.Socket;
import cs455.overlay.node.Manager;
import cs455.overlay.transport.TCPConnection;

public class RoutingEntry {
	
	public int id;
	public String ip;
	public int port;
	public TCPConnection tcp;
	
	public RoutingEntry(int id, String ip, int port, Manager m){
		this.id = id;
		this.ip = ip;
		this.port = port;
		try {
			if(m != null)
				tcp = new TCPConnection(new Socket(ip, port), m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
