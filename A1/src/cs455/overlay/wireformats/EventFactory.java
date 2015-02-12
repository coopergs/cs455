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
			}
		} catch (IOException e) {
			System.out.println("Error reading message.");
		}
	}
	
	private void OverlayNodeSendsRegistration(DataInputStream in){
		try {
			int length = in.readInt();
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
			int length = in.readInt();
			byte[] messageBytes = new byte[length];
			String message = new String(messageBytes);
			System.out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
