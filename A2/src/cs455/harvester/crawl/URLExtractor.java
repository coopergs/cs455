package cs455.harvester.crawl;

import net.htmlparser.jericho.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import cs455.harvester.crawlgraph.CrawlGraph;
import cs455.harvester.crawlgraph.GraphNode;
import cs455.harvester.tasks.CrawlTask;
import cs455.harvester.threadpool.Pool;

public class URLExtractor {
	
	private boolean inDomain(String domain, String url){
		if(url.contains(domain))
			return true;
		return false;
	}
	
	public void crawl(String domain, String extension, CrawlGraph graph, int depth, Pool p) {
		 Config.LoggerProvider = LoggerProvider.DISABLED;
		 try {
			 String pageUrl = domain;
			 if(!domain.equals(extension))
				 pageUrl = domain + extension;
			 HttpURLConnection con = (HttpURLConnection)(new URL(pageUrl).openConnection());
			 con.connect();
			 if(con.getResponseCode() == 404){
				 graph.addToBroken(extension);
				 return;
			 }
	         String resolvedUrl = con.getURL().toString();
	         if(inDomain(domain, resolvedUrl)){
	        	 if(!pageUrl.equals(resolvedUrl) && depth>0){
	        		 String subResolvedUrl = resolvedUrl.substring(domain.length(), resolvedUrl.length()-1);
	        		 if(!graph.exists(subResolvedUrl)){
	        			 graph.addNew(new GraphNode(subResolvedUrl));
	        			 p.addTask(new CrawlTask(domain, subResolvedUrl, graph, depth-1, p));
	        		 }
	        	 }
	         }
	         //Source source = new Source(resolvedUrl);
			 Source source = new Source(new URL(pageUrl));
			 List<Element> aTags = source.getAllElements(HTMLElementName.A);
			 for (Element aTag : aTags) {
				 if(aTag.getAttributeValue("href")==null){
					 //null reference - discard
				 }else if(aTag.getAttributeValue("href").equals("/")){
					 //is reference to current page - discard
				 }else if(aTag.getAttributeValue("href").equals("#")){
					 //discard
				 }else if(inDomain(domain, aTag.getAttributeValue("href"))){
					 if(domain.equals(aTag.getAttributeValue("href"))){
						 graph.addToIn(domain, extension);
						 graph.addToOut(extension, domain);
						 continue;
					 }
					 String subUrl = aTag.getAttributeValue("href").substring(domain.length(), aTag.getAttributeValue("href").length()-1);
					 graph.addToOut(extension, subUrl);
					 if(!graph.exists(subUrl)){
						 graph.addNew(new GraphNode(subUrl));
						 if(depth>0 && !subUrl.endsWith(".pdf") && !subUrl.endsWith(".doc") && !subUrl.endsWith(".pd"))
							 p.addTask(new CrawlTask(domain, subUrl, graph, depth-1, p));
					 }
					  graph.addToIn(subUrl, extension);
					 //System.out.println("IN DOMAIN "  + subUrl);
				 }else if(aTag.getAttributeValue("href").startsWith("/")){
					 graph.addToOut(extension, aTag.getAttributeValue("href"));
					 if(!graph.exists(aTag.getAttributeValue("href"))){
						 graph.addNew(new GraphNode(aTag.getAttributeValue("href")));
						 if(depth>0)
							 p.addTask(new CrawlTask(domain, aTag.getAttributeValue("href"), graph, depth-1, p));
					 }
					 graph.addToIn(aTag.getAttributeValue("href"), extension);
					 //System.out.println("SUB URL " + aTag.getAttributeValue("href"));
				 }else{
					 //System.out.println("boop " + aTag.getAttributeValue("href"));//Outside domain, don't crawl
				 }
			 }
			 //graph.traverseMap();
			 
		 } catch (IOException e) {
			 System.err.println(e.getMessage());
		 }
	}
}
