package cs455.overlay.node;

import java.net.Socket;
import java.util.Scanner;

import cs455.overlay.wireformats.*;

/**
 * 
 * @author Cooper Scott
 * CS455 - Overlay
 * cs455.overlay.node.MessagingNode.java
 * Executable Class that works as a node in the network
 *
 */

public class MessagingNode implements Node{
	
	/**
	 * Main method
	 * args - registry-host - host name of the registry to be used (String)
	 * args - registry-port - port number of the registry to be used (int)
	 */
	
	private String regHost;
	private int regPort;
	private Socket regSocket;
	
//	private void connectToRegistry(){
//		  try{
//				regSocket = new Socket(regHost, regPort);
//			    System.out.println("Connected to port 5555");
//			    DataOutputStream dout = new DataOutputStream(regSocket.getOutputStream());
//			    Protocol p = new OverlayNodeReportsTrafficSummary(1,2,3,4,5,6);
//			    byte[] data = p.getBytes();
//			    dout.writeInt(data.length);
//			    dout.write(data, 0, data.length);
//			   } catch (Exception e) {
//			     System.out.println("Unable to connect to host or port");
//			     e.printStackTrace();
//			     System.exit(1);
//			   }
//	}
	
	private void usage(){
		System.err.println("usage: java MessagingNode registry-host registry-port");
		System.err.println("registry-host: host name of the registry to connect to");
		System.err.println("registry-port: port number of the host to use");
		System.exit(1);
	}
	
	public void processEvent(Event e){
		if(e.message instanceof RegistryReportsRegistrationStatus)
			acceptRegistrationStatus(e);
		else if(e.message instanceof RegistryReportDeregistrationStatus)
			acceptDeregistrationStatus(e);
		else if(e.message instanceof RegistrySendsNodeManifest)
			processManifest(e);
		else if(e.message instanceof RegistryRequestsTaskInitiate)
			taskInitiate(e);
		else if(e.message instanceof RegistryRequestsTrafficSummary)
			trafficSummary(e);
	}
	
	private void acceptRegistrationStatus(Event e){
		//Accept Registration
	}
	
	private void acceptDeregistrationStatus(Event e){
		//Accept Deregistration
	}
	
	private void processManifest(Event e){
		//Process Manifest
	}
	
	private void taskInitiate(Event e){
		//task initiate
	}
	
	private void trafficSummary(Event e){
		//traffic Summary
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
