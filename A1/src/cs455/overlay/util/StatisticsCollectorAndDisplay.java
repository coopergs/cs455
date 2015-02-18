package cs455.overlay.util;

import java.util.ArrayList;

import cs455.overlay.node.Node;
import cs455.overlay.wireformats.OverlayNodeReportsTrafficSummary;

public class StatisticsCollectorAndDisplay {
	
	TrafficData[] data;
	
	public StatisticsCollectorAndDisplay(ArrayList<Node> nodes){
		data = new TrafficData[nodes.size()];
		for(int i=0;i<data.length;i++)
			data[i].id = nodes.get(i).getID();
	}
	
	public void recieveData(OverlayNodeReportsTrafficSummary r){
		for(int i=0;i<data.length;i++){
			if(r.id == data[i].id){
				data[i].recieveCount = r.packetsRecieved;
				data[i].sendCount = r.packetsSent;
				data[i].relayCount = r.packetsRelayed;
				data[i].recieveSum = r.sumRecieved;
				data[i].sendSum = r.sumSent;
			}
				
		}
	}
	
	public void display(){
		
		for(int i=0;i<data.length;i++){
			System.out.printf("ID:%d  Packets Sent:%d  Packets Recieved:%d  Packets Relayed:%d  Sum Sent:%d  Sum Recieved:%d\n", data[i].id, data[i].sendCount, data[i].recieveCount, data[i].relayCount, data[i].sendSum, data[i].recieveSum);
		}
		//System.out.printf("Sum  Packets Sent:%d  Packets Recieved:%d  Packets Relayed:%d  Sum Sent:%d  Sum Recieved:%d\n");
	}

}
