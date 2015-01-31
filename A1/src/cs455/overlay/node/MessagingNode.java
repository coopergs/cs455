package cs455.overlay.node;

/**
 * 
 * @author Cooper Scott
 * CS455 - Overlay
 * MessagingNode.java
 * Executable Class that works as a node in the network
 *
 */

public class MessagingNode {
	
	/**
	 * Main method
	 * args - registry-host - host name of the registry to be used (String)
	 * args - registry-port - port number of the registry to be used (int)
	 */
	
	private void usage(){
		System.err.println("usage: java MessagingNode registry-host registry-port");
		System.err.println("registry-host: host name of the registry to connect to");
		System.err.println("registry-port: port number of the host to use");
	}
	
	public static void Main(String[] args){
		MessagingNode messagingnode = new MessagingNode();
		messagingnode.usage();
		
	}

}
