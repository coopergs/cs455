package cs455.harvester.transport;

public class OtherDomain {
	
	public final String host;
	public final int port;
	public final String domain;
	
	
	public OtherDomain(String hostname, int portnum, String domain){
		host = hostname;
		port = portnum;
		this.domain = domain;
	}

}
