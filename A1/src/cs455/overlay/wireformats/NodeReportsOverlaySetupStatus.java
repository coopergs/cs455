package cs455.overlay.wireformats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NodeReportsOverlaySetupStatus implements Protocol{
	
	public int status;
	public String info;
	
	public NodeReportsOverlaySetupStatus(int status, String info){
		this.status = status;
		this.info = info;
	}
	
	public byte[] getBytes(){
		byte[] bytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		try {
			dout.writeInt(NODE_REPORTS_OVERLAY_SETUP_STATUS);
			dout.writeInt(status);
			dout.writeByte(info.getBytes().length);
			dout.write(info.getBytes());
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