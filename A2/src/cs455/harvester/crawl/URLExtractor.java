package cs455.harvester.crawl;

import net.htmlparser.jericho.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import cs455.harvester.crawlgraph.CrawlGraph;
import cs455.harvester.crawlgraph.GraphNode;

public class URLExtractor {
	
	private boolean inDomain(String domain, String url){
		if(url.contains(domain))
			return true;
		return false;
	}
	
	public void crawl(String domain, String extension, CrawlGraph graph, int depth) {
		 Config.LoggerProvider = LoggerProvider.DISABLED;
		 try {
			 String pageUrl = domain;
			 if(!domain.equals(extension))
				 pageUrl = domain + extension;
			 Source source = new Source(new URL(pageUrl));
			 List<Element> aTags = source.getAllElements(HTMLElementName.A);
			 for (Element aTag : aTags) {
				 if(aTag.getAttributeValue("href").equals("/")){
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
					 }
					  graph.addToIn(subUrl, extension);
					 System.out.println("IN DOMAIN "  + subUrl);
				 }else if(aTag.getAttributeValue("href").startsWith("/")){
					 graph.addToOut(extension, aTag.getAttributeValue("href"));
					 if(!graph.exists(aTag.getAttributeValue("href"))){
						 graph.addNew(new GraphNode(aTag.getAttributeValue("href")));
					 }
					 graph.addToIn(aTag.getAttributeValue("href"), extension);
					 System.out.println("SUB URL " + aTag.getAttributeValue("href"));
				 }else{
					 //System.out.println("boop " + aTag.getAttributeValue("href"));//Outside domain, don't crawl
				 }
			 }
			 graph.traverseMap();
			 
		 } catch (IOException e) {
			 System.err.println(e.getMessage());
		 }
	}
}
