package cs455.overlay.wireformats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OverlayNodeSendsData implements Protocol{
	
	private int destination;
	private int source;
	private int payload;
	private int[] path;
	
	public OverlayNodeSendsData(int destination, int source, int payload, int[] path){
		this.destination = destination;
		this.source = source;
		this.payload = payload;
		this.path = path;
	}
	
	public byte[] getBytes(){
		byte[] bytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		try {
			dout.writeInt(OVERLAY_NODE_SENDS_DATA);
			dout.writeInt(destination);
			dout.writeInt(source);
			dout.writeInt(payload);
			dout.writeInt(path.length);
			for(int i=0;i<path.length;i++)
				dout.writeInt(path[i]);
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