package cs455.overlay.wireformats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OverlayNodeReportsTrafficSummary implements Protocol{
	
	public int id;
	public int packetsSent;
	public int packetsRelayed;
	public long sumSent;
	public int packetsRecieved;
	public long sumRecieved;
	
	public OverlayNodeReportsTrafficSummary(int id, int packetsSent, int packetsRelayed, long sumSent, int packetsRecieved, long sumRecieved){
		this.id = id;
		this.packetsSent = packetsSent;
		this.packetsRelayed = packetsRelayed;
		this.sumSent = sumSent;
		this.packetsRecieved = packetsRecieved;
		this.sumRecieved = sumRecieved;
	}
	
	public byte[] getBytes(){
		byte[] bytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		try {
			dout.writeInt(OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY);
			dout.writeInt(id);
			dout.writeInt(packetsSent);
			dout.writeInt(packetsRelayed);
			dout.writeLong(sumSent);
			dout.writeInt(packetsRecieved);
			dout.writeLong(sumRecieved);
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