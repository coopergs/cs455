package cs455.overlay.wireformats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OverlayNodeSendsDeregistration implements Protocol{
	
	public String ip;
	public int port;
	public int id;
	
	public OverlayNodeSendsDeregistration(String ip, int port, int id){
		this.ip = ip;
		this.port = port;
		this.id = id;
	}
	
	public byte[] getBytes(){
		byte[] bytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		try {
			dout.writeInt(OVERLAY_NODE_SENDS_DEREGISTRATION);
			dout.writeByte(ip.getBytes().length);
			dout.write(ip.getBytes());
			dout.writeInt(port);
			dout.writeInt(id);
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