package cs455.overlay.wireformats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OverlayNodeSendsRegistration implements Protocol{
	
	private String ip;
	private int port;
	
	public OverlayNodeSendsRegistration(String ip, int port){
		this.ip = ip;
		this.port = port;
	}
	
	public byte[] getBytes(){
		byte[] bytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		try {
			dout.writeInt(OVERLAY_NODE_SENDS_REGISTRATION);
			dout.writeInt(ip.getBytes().length);
			dout.write(ip.getBytes());
			dout.writeInt(port);
			dout.flush();
			bytes = baOutputStream.toByteArray();
			baOutputStream.close();
			dout.close();
			return bytes;
		} catch (IOException e) {
			System.out.println("Error with sending information");
			return null;
		}
	}

}
