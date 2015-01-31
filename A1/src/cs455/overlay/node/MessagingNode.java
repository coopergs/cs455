package cs455.overlay.node;

import java.util.Scanner;

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
	
	private String regHost;
	private int regPort;
	
	private void usage(){
		System.err.println("usage: java MessagingNode registry-host registry-port");
		System.err.println("registry-host: host name of the registry to connect to");
		System.err.println("registry-port: port number of the host to use");
		System.exit(1);
	}
	
	public static void main(String[] args){
		MessagingNode messagingnode = new MessagingNode();
		
		//Checking that arguments are there and correct
		try{
			messagingnode.regHost = args[0];
			messagingnode.regPort = Integer.parseInt(args[1]);
		}catch(Exception e){
			messagingnode.usage();
		}
		
		//command loop
		Scanner kb = new Scanner(System.in);
		while(true){
			String input = kb.nextLine();
			if(input.equals("print-counters-and-diagnostics"))
				System.out.println("print-counters-and-diagnostics");
			else if(input.equals("exit-overlay"))
				break;
			else
				System.err.printf("Unrecognized command \"%s\". Try again\n", input);
		}
		kb.close();
	}

}
