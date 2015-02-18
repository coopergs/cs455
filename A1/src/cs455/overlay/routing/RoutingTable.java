package cs455.overlay.routing;

import cs455.overlay.node.Manager;

public class RoutingTable {
	
	public int[] allids;
	public RoutingEntry[] entrys;
	public Manager manager;
	
	public RoutingTable(int[] ids, String[] ips, int[] ports, int[] allids, Manager m){
		this.allids = allids;
		entrys = new RoutingEntry[ids.length];
		for(int i=0;i<ids.length;i++)
			entrys[i] = new RoutingEntry(ids[i], ips[i], ports[i]);
		manager = m;
	}

}
