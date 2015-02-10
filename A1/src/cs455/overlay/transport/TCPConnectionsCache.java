package cs455.overlay.transport;

import java.util.ArrayList;

/**
 * 
 * @author Cooper Scott
 * CS455 - Overlay
 * cs455.overlay.transport.TCPConnectionsCache.java
 * Object that is part of TCPServerThread. Whenever a connection is made, the thread gives the connection to
 * this object and it is stored and an id is returned
 *
 */

public class TCPConnectionsCache {
	
	private ArrayList<TCPConnection> store;
	
	public TCPConnectionsCache(){
		store = new ArrayList<TCPConnection>();
	}
	
	public int add(TCPConnection c){
		store.add(c);
		return store.indexOf(c);
	}
	
	public TCPConnection get(int i){
		return store.get(i);
	}
	
	public void closeAll(){
		for(TCPConnection tcp: store){
			tcp.closeConnection();
			store.remove(tcp);
		}
	}

}
