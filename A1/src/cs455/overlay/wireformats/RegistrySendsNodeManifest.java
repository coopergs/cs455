package cs455.overlay.wireformats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RegistrySendsNodeManifest implements Protocol {
	
	private int[] ids;
	private String[] ips;
	private int[] ports;
	private int[] totalIds;
	
	public RegistrySendsNodeManifest(int[] ids, String[] ips, int[] ports, int[] totalIds){
		this.ids = ids;
		this.ips = ips;
		this.ports = ports;
		this.totalIds = totalIds;
	}

	public byte[] getBytes() {
		byte[] bytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		try {
			dout.writeInt(Protocol.REGISTRY_SENDS_NODE_MANIFEST);
			dout.writeByte(ids.length);
			for(int i=0;i<ids.length;i++){
				dout.writeInt(ids[i]);
				dout.writeByte(ips[i].getBytes().length);
				dout.write(ips[i].getBytes());
				dout.writeInt(ports[i]);
			}
			dout.writeByte(totalIds.length);
			for(int i=0;i<totalIds.length;i++)
				dout.writeInt(totalIds[i]);
			dout.flush();
			bytes = baOutputStream.toByteArray();
			baOutputStream.close();
			dout.close();
		} catch (IOException e) {
			System.out.println("Error sending data.");
		}
		return bytes;
	}

}
