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
	
	public Event createEvent(byte[] b){
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(b);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		Event e = null;
		try {
			int messageType = din.readInt();
			switch(messageType){
			case Protocol.OVERLAY_NODE_SENDS_REGISTRATION:
				e = OverlayNodeSendsRegistration(din);
				break;
			case Protocol.REGISTRY_REPORTS_REGISTRATION_STATUS:
				e = RegistryReportsRegistrationStatus(din);
				break;
			case Protocol.OVERLAY_NODE_SENDS_DEREGISTRATION:
				e = OverlayNodeSendsDeregistration(din);
				break;
			case Protocol.REGISTRY_REPORTS_DEREGISTRATION_STATUS:
				e = RegistryReportsDeregistrationStatus(din);
				break;
			case Protocol.REGISTRY_SENDS_NODE_MANIFEST:
				e = RegistrySendsNodeManifest(din);
				break;
			case Protocol.NODE_REPORTS_OVERLAY_SETUP_STATUS:
				e = NodeReportsOverlaySetupStatus(din);
				break;
			case Protocol.REGISTRY_REQUESTS_TASK_INITIATE:
				e = RegistryRequestsTaskInitiate(din);
				break;
			case Protocol.OVERLAY_NODE_SENDS_DATA:
				e = OverlayNodeSendsData(din);
				break;
			case Protocol.OVERLAY_NODE_REPORTS_TASK_FINISHED:
				e = OverlayNodeReportsTaskFinished(din);
				break;
			case Protocol.REGISTRY_REQUESTS_TRAFFIC_SUMMARY:
				e = RegistryRequestsTrafficSummary(din);
				break;
			case Protocol.OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY:
				OverlayNodeReportsTrafficSummary(din);
				break;
			}
			din.close();
			baInputStream.close();
		} catch (IOException e1) {
			System.out.println("Error reading message.");
		}
		return e;
		
	}
	
	private Event OverlayNodeSendsRegistration(DataInputStream in){
		try {
			byte length = in.readByte();
			byte[] ipBytes = new byte[length];
			in.read(ipBytes, 0, length);
			String ip = new String(ipBytes);
			int port = in.readInt();
			return new Event(new OverlayNodeSendsRegistration(ip, port));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Event RegistryReportsRegistrationStatus(DataInputStream in){
		try {
			int status = in.readInt();
			byte length = in.readByte();
			byte[] messageBytes = new byte[length];
			in.readFully(messageBytes, 0, length);
			String message = new String(messageBytes);
			return new Event(new RegistryReportsRegistrationStatus(status, message));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Event OverlayNodeSendsDeregistration(DataInputStream in){
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
			return new Event(new OverlayNodeSendsDeregistration(ip, port, id));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Event RegistryReportsDeregistrationStatus(DataInputStream in){
		try {
			int status = in.readInt();
			byte length = in.readByte();
			byte[] messageBytes = new byte[length];
			in.readFully(messageBytes, 0, length);
			String message = new String(messageBytes);
			return new Event(new RegistryReportDeregistrationStatus(status, message));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Event RegistrySendsNodeManifest(DataInputStream in){
		try {
			int size = in.readByte();
			int[] ids = new int[size];
			String[] ips = new String[size];
			int[] ports = new int[size];
			for(int i=0;i<size;i++){
				ids[i] = in.readInt();
				byte length = in.readByte();
				byte[] ipBytes = new byte[length];
				in.readFully(ipBytes, 0, length);
				ips[i] = new String(ipBytes);
				ports[i] = in.readInt();
			}
			byte length = in.readByte();
			int[] allIds = new int[length];
			for(int i=0;i<length;i++){
				allIds[i] = in.readInt();
			}
			return new Event(new RegistrySendsNodeManifest(ids, ips, ports, allIds));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Event NodeReportsOverlaySetupStatus(DataInputStream in){
		try {
			int status = in.readInt();
			byte length = in.readByte();
			byte[] messageBytes = new byte[length];
			in.readFully(messageBytes, 0, length);
			String message = new String(messageBytes);
			return new Event(new NodeReportsOverlaySetupStatus(status, message));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Event RegistryRequestsTaskInitiate(DataInputStream in){
		try {
			int numPackets = in.readInt();
			return new Event(new RegistryRequestsTaskInitiate(numPackets));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Event OverlayNodeSendsData(DataInputStream in){
		try {
			int destination = in.readInt();
			int source = in.readInt();
			int payload = in.readInt();
			int pathLength = in.readInt();
			int[] path = new int[pathLength];
			for(int i=0;i<pathLength;i++){
				path[i] = in.readInt();
			}
			return new Event(new OverlayNodeSendsData(destination, source, payload, path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Event OverlayNodeReportsTaskFinished(DataInputStream in){
		try {
			int length = in.readInt();
			byte[] ipBytes = new byte[length];
			in.readFully(ipBytes, 0, length);
			String ip = new String(ipBytes);
			int port = in.readInt();
			int id = in.readInt();
			return new Event(new OverlayNodeReportsTaskFinished(ip, port, id));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Event RegistryRequestsTrafficSummary(DataInputStream in){
		return new Event(new RegistryRequestsTrafficSummary());
	}
	
	private Event OverlayNodeReportsTrafficSummary(DataInputStream in){
		try {
			int id = in.readInt();
			int packetsSent = in.readInt();
			int packetsRelayed = in.readInt();
			long sumSent = in.readLong();
			int packetsRecieved = in.readInt();
			long sumRecieved = in.readLong();
			return new Event(new OverlayNodeReportsTrafficSummary(id, packetsSent, packetsRelayed, sumSent, packetsRecieved, sumRecieved));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
