package cs455.harvester.crawlgraph;

import java.util.ArrayList;

public class GraphNode {
	
	public String url;
	public ArrayList<String> in;
	public ArrayList<String> out;
	
	public GraphNode(String url){
		in = new ArrayList<String>();
		out = new ArrayList<String>();
		this.url = url;
	}

}
