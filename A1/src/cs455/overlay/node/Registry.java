package cs455.overlay.node;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import cs455.overlay.transport.TCPConnection;
import cs455.overlay.transport.TCPSender;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.wireformats.*;

/**
 * 
 * @author Cooper Scott
 * CS455 - Overlay
 * cs455.overlay.node.Registry.java
 * Executable Class that works as the registry for the overlay
 *
 */

public class Registry implements Manager{
	
	/**
	 * Main method
	 * args - portnum - port number to be used communicating with messaging nodes (int)
	 */
	public final static int MAX_OVERLAY_NODES = 128;
	
	private int portnum;
	private Thread serverThread;
	private Integer[] nodeIDs;
	private int nodeIDcount;
	private ArrayList<Node> nodes;
	
	private void usage(){
		System.err.println("usage: java Registry portnum");
		System.err.println("portnum: port number to be used communicating with messaging nodes");
		System.exit(1);
	}
	
	public void processEvent(Event e, TCPConnection tcp){
		if(e.message instanceof OverlayNodeSendsRegistration)
			registerNode(e, tcp);
		else if(e.message instanceof OverlayNodeSendsDeregistration)
			deregisterNode(e);
		else if(e.message instanceof NodeReportsOverlaySetupStatus)
			acceptSetupStatus(e);
		else if(e.message instanceof OverlayNodeReportsTaskFinished)
			acceptTaskFinished(e);
		else if(e.message instanceof OverlayNodeReportsTrafficSummary)
			acceptTrafficSummary(e);
	}
	
	private void registerNode(Event e, TCPConnection tcp){
		OverlayNodeSendsRegistration r = (OverlayNodeSendsRegistration) e.message;
		RegistryReportsRegistrationStatus r1;
		try{
			if(r.ip == null || r.port == 0){
				r1 = new RegistryReportsRegistrationStatus(-1, "Invalid Registration Arguments");
			}else if(nodeIDcount == MAX_OVERLAY_NODES){
				r1 = new RegistryReportsRegistrationStatus(-1, "Maximum Overlay Nodes Reached");
			}else{
				TCPSender sender = new TCPSender(new Socket(r.ip, r.port));
				nodes.add(new Node(r.ip, r.port, nodeIDs[nodeIDcount], sender));
				r1 = new RegistryReportsRegistrationStatus(nodeIDs[nodeIDcount], "Successfully Registered");
			}
			tcp.sendData(r1.getBytes());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void deregisterNode(Event e){
		//deregister node
		//send confirmation
	}
	
	private void acceptSetupStatus(Event e){
		//accept setup status
	}
	
	private void acceptTaskFinished(Event e){
		//accept task finished
	}
	
	private void acceptTrafficSummary(Event e){
		//accept traffic Summary
	}
	
	public static void main(String[] args){
		Registry registry = new Registry();
		
		//Checking that arguments are there and correct
		try{
			registry.portnum = Integer.parseInt(args[0]);
		}catch(Exception e){
			registry.usage();
		}
		
		//node setup
		registry.nodeIDcount = 0;
		registry.nodeIDs = new Integer[MAX_OVERLAY_NODES];
		registry.nodes = new ArrayList<Node>();
		for(int i=0;i<MAX_OVERLAY_NODES;i++){
			registry.nodeIDs[i] = i;
		}
		Collections.shuffle(Arrays.asList(registry.nodeIDs));
		
		try {
			registry.serverThread = new Thread(new TCPServerThread(registry.portnum, registry));
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
