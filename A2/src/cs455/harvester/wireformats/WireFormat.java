package cs455.harvester.wireformats;

public interface WireFormat {
	
	public final int TASK_HAND_OFF = 1;
	public final int HAND_OFF_ACK = 2;
	public final int CRAWLING_COMPLETION = 3;
	
	public byte[] getBytes();
}
