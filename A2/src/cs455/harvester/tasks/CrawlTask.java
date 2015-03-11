package cs455.harvester.tasks;

import cs455.harvester.Crawler;
import cs455.harvester.crawlgraph.CrawlGraph;
import cs455.harvester.threadpool.Pool;

public class CrawlTask implements Task{
	
	private String domain;
	private String extension;
	private CrawlGraph graph;
	private int depth;
	private Pool pool;
	
	public CrawlTask(String domain, String ext, CrawlGraph graph, int depth, Pool p){
		this.domain = domain;
		this.extension = ext;
		this.graph = graph;
		this.depth = depth;
		this.pool = p;
	}
	
	public void execute() {
		Crawler.urlEx.crawl(domain, extension, graph, depth, pool);
		System.out.printf("%s, %s, %d\n", domain, extension, depth);
	}

}
