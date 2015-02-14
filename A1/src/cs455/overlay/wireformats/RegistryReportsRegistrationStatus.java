package cs455.overlay.wireformats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RegistryReportsRegistrationStatus implements Protocol{
	
	private int status;
	private String message;
	
	public RegistryReportsRegistrationStatus(int status, String message){
		this.status = status;
		this.message = message;
	}
	
	public byte[] getBytes(){
		byte[] bytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		try {
			dout.writeInt(REGISTRY_REPORTS_REGISTRATION_STATUS);
			dout.writeInt(status);
			dout.writeByte(message.getBytes().length);
			dout.write(message.getBytes());
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