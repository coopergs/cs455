package cs455.overlay.node;

import cs455.overlay.transport.TCPConnection;
import cs455.overlay.wireformats.Event;

public interface Manager {
	
	public void processEvent(Event e, TCPConnection tcp);

}
