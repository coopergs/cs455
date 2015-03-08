package cs455.harvester.tasks;

import cs455.harvester.Crawler;
import cs455.harvester.crawlgraph.CrawlGraph;

public class CrawlTask implements Task{
	
	private String domain;
	private String extension;
	private CrawlGraph graph;
	private int depth;
	
	public CrawlTask(String domain, String ext, CrawlGraph graph, int depth){
		this.domain = domain;
		this.extension = ext;
		this.graph = graph;
		this.depth = depth;
	}
	
	public void execute() {
		Crawler.urlEx.crawl(domain, extension, graph, depth);
	}

}
