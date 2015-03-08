package cs455.harvester.crawlgraph;

import java.util.Collection;
import java.util.HashMap;

public class CrawlGraph {
	
	private HashMap<String, GraphNode> map;
	
	public CrawlGraph(){
		map = new HashMap<String, GraphNode>();
	}
	
	public synchronized void addNew(GraphNode node){
		map.put(node.url, node);
	}
	
	public synchronized boolean exists(String url){
		return map.get(url) != null;
	}
	
	public synchronized void addToIn(String url, String newIn){
		if(!map.get(url).in.contains(newIn))
			map.get(url).in.add(newIn);
	}
	
	public synchronized void addToOut(String url, String out){
		if(!map.get(url).out.contains(out))
			map.get(url).out.add(out);
	}
	
	public synchronized void traverseMap(){
		Collection<GraphNode> collection = map.values();
		for(GraphNode gn: collection){
			System.out.println(gn.url);
			for(String s: gn.in){
				System.out.println("	IN: " + s);
			}
			for(String s: gn.out){
				System.out.println("	OUT:" + s);
			}
		}
	}

}
