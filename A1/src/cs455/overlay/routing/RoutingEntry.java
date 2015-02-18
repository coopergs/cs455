package cs455.overlay.routing;

import java.net.Socket;

import cs455.overlay.transport.TCPSender;

public class RoutingEntry {
	
	public int id;
	public String ip;
	public int port;
	public Thread tcp;
	public TCPSender sender;
	
	public RoutingEntry(int id, String ip, int port){
		this.id = id;
		this.ip = ip;
		this.port = port;
		try {
				sender = new TCPSender(new Socket(ip, port));
				tcp = new Thread(sender);
				tcp.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
