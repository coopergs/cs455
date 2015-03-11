package cs455.harvester.wireformats;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class Decoder {
	
	private static final Decoder instance = new Decoder();
	
	private Decoder(){}
	
	public static Decoder getInstance(){
		return instance;
	}
	
	public void decodeAndAct(byte[] bytes){
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		try {
			int messageType = din.readInt();
			switch(messageType){
			case WireFormat.TASK_HAND_OFF:
				//hand off stuff
				break;
			case WireFormat.HAND_OFF_ACK:
				//stuff
				break;
			case WireFormat.CRAWLING_COMPLETION:
				//stuff
				break;
			}
			din.close();
			baInputStream.close();
		} catch (IOException e1) {
			System.out.println("Error reading message.");
		}
	}

}
