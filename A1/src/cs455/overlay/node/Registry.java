package cs455.overlay.node;

import java.util.Scanner;

import cs455.overlay.transport.TCPServerThread;

/**
 * 
 * @author Cooper Scott
 * CS455 - Overlay
 * cs455.overlay.node.Registry.java
 * Executable Class that works as the registry for the overlay
 *
 */

public class Registry {
	
	/**
	 * Main method
	 * args - portnum - port number to be used communicating with messaging nodes (int)
	 */
	private int portnum;
	private Thread serverThread;
	
	private void usage(){
		System.err.println("usage: java Registry portnum");
		System.err.println("portnum: port number to be used communicating with messaging nodes");
		System.exit(1);
	}
	
	public static void main(String[] args){
		Registry registry = new Registry();
		
		//Checking that arguments are there and correct
		try{
			registry.portnum = Integer.parseInt(args[0]);
		}catch(Exception e){
			registry.usage();
		}
		
		try {
			registry.serverThread = new Thread(new TCPServerThread(registry.portnum));
			registry.serverThread.start();
		} catch (Exception e) {
			System.err.println("Unable to listen on port " + registry.portnum);
			System.exit(1);
		}
		
		//command loop
		Scanner kb = new Scanner(System.in);
		while(true){
			String input = kb.nextLine();
			if(input.equals("list-messaging-nodes"))
				System.out.println("list-messaging-nodes");
			else if(input.split(" ")[0].equals("setup-overlay"))
				System.out.println("setup-overlay");
			else if(input.equals("list-routing-tables"))
				System.out.println("list-routing-tables");
			else if(input.equals("start"))
				System.out.println("start");
			else if(input.equals("exit"))
				break;
			else
				System.err.printf("Unrecognized command \"%s\". Try again\n", input);
		}
		registry.serverThread.interrupt();
		try {
			registry.serverThread.join();
		} catch (InterruptedException e) {
			System.err.println("Thread error occurred");
		}
		System.out.println("Registry Closed.");
		kb.close();
	}

}
