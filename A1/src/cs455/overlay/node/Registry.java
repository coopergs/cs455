package cs455.overlay.node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import cs455.overlay.routing.RoutingTable;
import cs455.overlay.transport.TCPConnection;
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
	private RoutingTable[] tables;
	private int successfulOverlaySetups = 0;
	
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
				nodes.add(new Node(r.ip, r.port, nodeIDs[nodeIDcount], tcp));
				System.out.println("Registered messaging node. ID: " + nodeIDs[nodeIDcount]);
				r1 = new RegistryReportsRegistrationStatus(nodeIDs[nodeIDcount], "Successfully Registered");
				nodeIDcount++;
			}
			tcp.sendData(r1.getBytes());
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void deregisterNode(Event e){
		OverlayNodeSendsDeregistration r = (OverlayNodeSendsDeregistration) e.message;
		RegistryReportDeregistrationStatus r2;
		try{
			Node node = null;
			for(int i=0;i<nodes.size();i++){
				if(nodes.get(i).getID() == r.id)
					node = nodes.get(i);
			}
			r2 = new RegistryReportDeregistrationStatus(1, "Sucessfully Deregistered");
			if(node != null)
				node.tcp.sendData(r2.getBytes());
			System.out.println("Deregistered messaging node. ID: " + node.getID());
			nodes.remove(node);
		}catch(Exception e1){
			e1.printStackTrace();
		}
	}
	
	private void acceptSetupStatus(Event e){
		successfulOverlaySetups++;
		if(successfulOverlaySetups == nodes.size())
			System.out.println("All nodes report sucessful overlay setup.");
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
			if(input.equals("list-messaging-nodes")){
				Collections.sort(registry.nodes);
				if(registry.nodes.isEmpty()){
					System.out.println("No messaging nodes");
				}
				for(Node n: registry.nodes)
					System.out.printf("Node id: %d, Hostname: %s, Port Number: %d\n", n.getID(), n.getIP(), n.getPort());
			}else if(input.split(" ")[0].equals("setup-overlay")){
				Collections.sort(registry.nodes);
				if(registry.nodes.size()<10)
					System.out.println("Cannot setup overlay with less than 10 registered messaging nodes.");
				else{
					try{
						registry.tables = new RoutingTable[registry.nodes.size()];
						int Rn = Integer.parseInt(input.split(" ")[1]);
						if(Rn<=0)
							throw new Exception();
						int i;
						for(i = 1;i<registry.nodes.size();i++){
							i = 2*i;
						}
						if(Rn>=registry.nodes.size())
							throw new Exception();
						int[] totalIds = new int[registry.nodes.size()];
						int[] ids = new int[Rn];
						String[] ips = new String[Rn];
						int[] ports = new int[Rn];
						for(i=0;i<registry.nodes.size();i++){
							totalIds[i] = registry.nodes.get(i).getID();
						}
						for(int nodenum = 0;nodenum<registry.nodes.size();nodenum++){
							int node = 1;
							for(i=0;i<Rn;i++){
								int newnum = nodenum + node;
								if(newnum>=registry.nodes.size())
									newnum = newnum - registry.nodes.size();
								ids[i] = registry.nodes.get(newnum).getID();
								ips[i] = registry.nodes.get(newnum).getIP();
								ports[i] = registry.nodes.get(newnum).getPort();
								node = 2*node;
							}
							RegistrySendsNodeManifest r = new RegistrySendsNodeManifest(ids, ips, ports, totalIds);
							registry.nodes.get(nodenum).tcp.sendData(r.getBytes());
							registry.tables[i] = new RoutingTable(ids, ips, ports, totalIds, null);
						}
						System.out.println("Sending overlay to nodes. Please wait.");
					}catch(Exception e){
						System.out.println("Invalid argument for setup-overlay");
					}
				}
			}else if(input.equals("list-routing-tables")){
//				for(int i=0;i<registry.nodes.size();i++){
//					System.out.printf("\n\n\nRouting table for ip:%s port:%d id:%d\n", registry.nodes.get(i).getIP(), registry.nodes.get(i).getPort(), registry.nodes.get(i).getID());
//					for(int j=0;j<registry.tables[i].entrys.length;j++){
//						System.out.printf("     ip:%s port:%d id:%d\n", registry.tables[i].entrys[j].ip, registry.tables[i].entrys[j].port, registry.tables[i].entrys[j].id);
//					}
//				}
			}else if(input.equals("start"))
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
