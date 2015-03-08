package cs455.harvester;

import cs455.harvester.crawl.URLExtractor;
import cs455.harvester.crawlgraph.CrawlGraph;
import cs455.harvester.crawlgraph.GraphNode;

public class Crawler {
	
	private int port;
	private int pool_size;
	private String url;
	private String config_file;
	public static final URLExtractor urlEx = new URLExtractor();
	
	private void usage(){
		System.out.println("usage: java cs455.harvester.Crawler portnum thread-pool-size root-url path-to-config-file");
		System.out.println("	portnum: port to listen on");
		System.out.println("	thread-pool-size: number of threads to use in crawling");
		System.out.println("	root-url: domain to crawl");
		System.out.println("	path-to-config-file: path to file containing the info for the other crawlers");
		System.exit(1);
	}
	
	public static void main(String[] args){
		
		Crawler c = new Crawler();
		try{
			c.port = Integer.parseInt(args[0]);
			c.pool_size = Integer.parseInt(args[1]);
			c.url = args[2];
			c.config_file = args[3];
		}catch(Exception e){
			c.usage();
		}
		
		CrawlGraph graph = new CrawlGraph();
		GraphNode node = new GraphNode(c.url);
		graph.addNew(node);
		urlEx.crawl(c.url, c.url, graph, 5);
	}

}
