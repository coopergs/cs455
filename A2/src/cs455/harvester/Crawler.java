package cs455.harvester;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import cs455.harvester.transport.OtherDomain;
import cs455.harvester.transport.ServerThread;
import cs455.harvester.crawl.URLExtractor;
import cs455.harvester.crawlgraph.CrawlGraph;
import cs455.harvester.crawlgraph.GraphNode;
import cs455.harvester.tasks.CrawlTask;
import cs455.harvester.threadpool.Pool;

public class Crawler {
	
	private int port;
	private int pool_size;
	private String url;
	private File config_file;
	public static final URLExtractor urlEx = new URLExtractor();
	private ArrayList<OtherDomain> otherCrawlers;
	private ServerThread server;
	
	private void usage(){
		System.out.println("usage: java cs455.harvester.Crawler portnum thread-pool-size root-url path-to-config-file");
		System.out.println("	portnum: port to listen on");
		System.out.println("	thread-pool-size: number of threads to use in crawling");
		System.out.println("	root-url: domain to crawl");
		System.out.println("	path-to-config-file: path to file containing the info for the other crawlers");
		System.exit(1);
	}
	
	private void readFile(File f){
		try{
			otherCrawlers = new ArrayList<OtherDomain>();
			Scanner s = new Scanner(f);
			while(s.hasNextLine()){
				String next = s.nextLine();
				String host = next.split(":")[0];
				int port = Integer.parseInt(next.split(":")[1].split(",")[0]);
				String domain = next.split(",")[1];
				otherCrawlers.add(new OtherDomain(host, port, domain));
			}
			s.close();
		}catch(Exception e){
			System.out.println("File either not found or in incorrect form.");
			System.exit(1);
		}
		
	}
	
	public static void main(String[] args){
		
		Crawler c = new Crawler();
		try{
			c.port = Integer.parseInt(args[0]);
			c.pool_size = Integer.parseInt(args[1]);
			if(args[2].equals("http://www.bmb.colostate.edu/index.cfm")){
				args[2] = "http://www.bmb.colostate.edu/";

			}if(args[2].equals("http://www.cs.colostate.edu/cstop/index.html")){
				args[2] = "http://www.cs.colostate.edu/";
			}
			c.url = args[2];
			c.config_file = new File(args[3]);
			c.readFile(c.config_file);
		}catch(Exception e){
			c.usage();
		}
		
//		c.server = new ServerThread(c.port);
//		c.server.start();
		
		CrawlGraph graph = new CrawlGraph();
		GraphNode node = new GraphNode(c.url);
		graph.addNew(node);
		Pool p = new Pool(c.pool_size);
		p.addTask(new CrawlTask(c.url, c.url, graph, 5, p));
		p.matchPairs();
		System.out.println("make files");
	}

}
