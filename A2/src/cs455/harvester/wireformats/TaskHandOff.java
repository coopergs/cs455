package cs455.harvester.wireformats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TaskHandOff implements WireFormat{
	
	private String link;
	
	public TaskHandOff(String link){
		this.link = link;
	}

	public byte[] getBytes() {
		byte[] bytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		try {
			dout.writeInt(TASK_HAND_OFF);
			dout.writeInt(link.getBytes().length);
			dout.write(link.getBytes());
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
