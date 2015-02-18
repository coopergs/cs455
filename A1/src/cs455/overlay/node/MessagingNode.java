package cs455.overlay.node;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import cs455.overlay.transport.TCPConnection;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.wireformats.*;

/**
 * 
 * @author Cooper Scott
 * CS455 - Overlay
 * cs455.overlay.node.MessagingNode.java
 * Executable Class that works as a node in the network
 *
 */

public class MessagingNode implements Manager{
	
	/**
	 * Main method
	 * args - registry-host - host name of the registry to be used (String)
	 * args - registry-port - port number of the registry to be used (int)
	 */
	
	private String regHost;
	private int regPort;
	private TCPConnection registry;
	private TCPServerThread serverThread;
	private int recievingPort;
	private int nodeID;
	
	private void setReciever(){
		try {
			serverThread = new TCPServerThread(0, this);
			recievingPort = serverThread.getPort();
		} catch (Exception e) {
			System.out.println("Error setting up Reciever");
		}
	}
	
	private void connectToRegistry(){
		  try{
				registry = new TCPConnection(new Socket(regHost, regPort), this);
			    System.out.println("Connected to registry on port " + regPort);
			    OverlayNodeSendsRegistration message = new OverlayNodeSendsRegistration(InetAddress.getLocalHost().getHostAddress(), recievingPort);
			    registry.sendData(message.getBytes());
			   } catch (Exception e) {
			     System.out.println("Unable to connect to host or port");
			     System.exit(1);
			   }
	}
	
	private void usage(){
		System.err.println("usage: java MessagingNode registry-host registry-port");
		System.err.println("registry-host: host name of the registry to connect to");
		System.err.println("registry-port: port number of the host to use");
		System.exit(1);
	}
	
	public void processEvent(Event e, TCPConnection tcp){
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
		RegistryReportsRegistrationStatus r = (RegistryReportsRegistrationStatus) e.message;
		System.out.println(r.message);
		nodeID = r.status;
		if(r.status < 0)
			System.exit(1);
	}
	
	private void acceptDeregistrationStatus(Event e){
		RegistryReportDeregistrationStatus r = (RegistryReportDeregistrationStatus) e.message;
		System.out.println(r.message);
		System.exit(0);
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
		
		messagingnode.setReciever();
		
		messagingnode.connectToRegistry();
		
		//command loop
		@SuppressWarnings("resource")
		Scanner kb = new Scanner(System.in);
		while(true){
			String input = kb.nextLine();
			if(input.equals("print-counters-and-diagnostics"))
				System.out.println("print-counters-and-diagnostics");
			else if(input.equals("exit-overlay")){
				try {
					OverlayNodeSendsDeregistration r = new OverlayNodeSendsDeregistration(InetAddress.getLocalHost().getHostAddress(), messagingnode.recievingPort, messagingnode.nodeID);
					messagingnode.registry.sendData(r.getBytes());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else
				System.err.printf("Unrecognized command \"%s\". Try again\n", input);
		}
	}

}
