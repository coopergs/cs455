package cs455.harvester.wireformats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CrawlingCompletion implements WireFormat{
	
	public CrawlingCompletion(){}

	public byte[] getBytes() {
		byte[] bytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		try {
			dout.writeInt(CRAWLING_COMPLETION);
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
