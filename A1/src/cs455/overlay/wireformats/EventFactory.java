package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class EventFactory {
	
	private static EventFactory instance = new EventFactory();
	
	private EventFactory(){}
	
	public static EventFactory getInstance(){
		return instance;
	}
	
	public void createEvents(byte[] b){
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(b);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		try {
			int messageType = din.readInt();
			switch(messageType){
			case Protocol.OVERLAY_NODE_SENDS_REGISTRATION:
				OverlayNodeSendsRegistration(din);
				break;
			case Protocol.REGISTRY_REPORTS_REGISTRATION_STATUS:
				RegistryReportsRegistrationStatus(din);
				break;
			case Protocol.OVERLAY_NODE_SENDS_DEREGISTRATION:
				OverlayNodeSendsDeregistration(din);
				break;
			case Protocol.REGISTRY_REPORTS_DEREGISTRATION_STATUS:
				RegistryReportsDeregistrationStatus(din);
				break;
			case Protocol.REGISTRY_SENDS_NODE_MANIFEST:
				RegistrySendsNodeManifest(din);
				break;
			case Protocol.NODE_REPORTS_OVERLAY_SETUP_STATUS:
				NodeReportsOverlaySetupStatus(din);
				break;
			case Protocol.REGISTRY_REQUESTS_TASK_INITIATE:
				RegistryRequestsTaskInitiate(din);
				break;
			case Protocol.OVERLAY_NODE_SENDS_DATA:
				OverlayNodeSendsData(din);
				break;
			case Protocol.OVERLAY_NODE_REPORTS_TASK_FINISHED:
				OverlayNodeReportsTaskFinished(din);
				break;
			case Protocol.REGISTRY_REQUESTS_TRAFFIC_SUMMARY:
				RegistryRequestsTrafficSummary(din);
				break;
			case Protocol.OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY:
				OverlayNodeReportsTrafficSummary(din);
				break;
			}
			din.close();
			baInputStream.close();
		} catch (IOException e) {
			System.out.println("Error reading message.");
		}
		
	}
	
	private void OverlayNodeSendsRegistration(DataInputStream in){
		try {
			byte length = in.readByte();
			byte[] ipBytes = new byte[length];
			in.read(ipBytes, 0, length);
			String ip = new String(ipBytes);
			System.out.println(ip);
			int port = in.readInt();
			System.out.println(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void RegistryReportsRegistrationStatus(DataInputStream in){
		try {
			int status = in.readInt();
			System.out.println(status);
			byte length = in.readByte();
			byte[] messageBytes = new byte[length];
			in.readFully(messageBytes, 0, length);
			String message = new String(messageBytes);
			System.out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void OverlayNodeSendsDeregistration(DataInputStream in){
		try {
			byte length = in.readByte();
			byte[] ipBytes = new byte[length];
			in.read(ipBytes, 0, length);
			String ip = new String(ipBytes);
			System.out.println(ip);
			int port = in.readInt();
			System.out.println(port);
			int id = in.readInt();
			System.out.println(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void RegistryReportsDeregistrationStatus(DataInputStream in){
		try {
			int status = in.readInt();
			System.out.println(status);
			byte length = in.readByte();
			byte[] messageBytes = new byte[length];
			in.readFully(messageBytes, 0, length);
			String message = new String(messageBytes);
			System.out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void RegistrySendsNodeManifest(DataInputStream in){
		try {
			int size = in.readByte();
			for(int i=0;i<size;i++){
				int id = in.readInt();
				System.out.println(i + " " + id);
				byte length = in.readByte();
				byte[] ipBytes = new byte[length];
				in.readFully(ipBytes, 0, length);
				String ip = new String(ipBytes);
				System.out.println(i + " " + ip);
				int port = in.readInt();
				System.out.println(i + " " + port);
			}
			byte length = in.readByte();
			for(int i=0;i<length;i++){
				int id = in.readInt();
				System.out.println(id);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void NodeReportsOverlaySetupStatus(DataInputStream in){
		try {
			int status = in.readInt();
			System.out.println(status);
			byte length = in.readByte();
			byte[] messageBytes = new byte[length];
			in.readFully(messageBytes, 0, length);
			String message = new String(messageBytes);
			System.out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void RegistryRequestsTaskInitiate(DataInputStream in){
		try {
			int numPackets = in.readInt();
			System.out.println(numPackets);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void OverlayNodeSendsData(DataInputStream in){
		try {
			int destination = in.readInt();
			System.out.println(destination);
			int source = in.readInt();
			System.out.println(source);
			int payload = in.readInt();
			System.out.println(payload);
			int pathLength = in.readInt();
			int[] path = new int[pathLength];
			for(int i=0;i<pathLength;i++){
				path[i] = in.readInt();
				System.out.println(path[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void OverlayNodeReportsTaskFinished(DataInputStream in){
		try {
			int length = in.readInt();
			byte[] ipBytes = new byte[length];
			in.readFully(ipBytes, 0, length);
			String ip = new String(ipBytes);
			System.out.println(ip);
			int port = in.readInt();
			System.out.println(port);
			int id = in.readInt();
			System.out.println(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void RegistryRequestsTrafficSummary(DataInputStream in){
		System.out.println("REGISTRY_REQUESTS_TRAFFIC_SUMMARY");
	}
	
	private void OverlayNodeReportsTrafficSummary(DataInputStream in){
		try {
			int id = in.readInt();
			System.out.println(id);
			int packetsSent = in.readInt();
			System.out.println(packetsSent);
			int packetsRelayed = in.readInt();
			System.out.println(packetsRelayed);
			long sumSent = in.readLong();
			System.out.println(sumSent);
			int packetsRecieved = in.readInt();
			System.out.println(packetsRecieved);
			long sumRecieved = in.readLong();
			System.out.println(sumRecieved);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
