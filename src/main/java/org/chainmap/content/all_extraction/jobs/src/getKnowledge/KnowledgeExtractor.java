package getKnowledge;

import java.io.IOException;
import java.util.Collection;

import org.apache.solr.client.solrj.*;
import org.apache.solr.client.solrj.impl.*;
import org.apache.solr.client.solrj.response.*;
import org.apache.solr.common.*;

public class KnowledgeExtractor {
	
	public static String extract(String tag) throws SolrServerException, IOException
	{
		String urlString = "http://107.181.170.169:8983/solr/chainmap";
		SolrClient solr = new HttpSolrClient.Builder(urlString).build();
		
		((HttpSolrClient) solr).setParser(new XMLResponseParser());
		
		SolrQuery query = new SolrQuery();
		query.set("fl", "title, url, summary, category");
		query.set("q", "category:knowledge AND search_content:" + tag);
		
		QueryResponse response = solr.query(query);
		
		SolrDocumentList list = response.getResults();
		
		SolrDocument first = null;
		if(list.size() > 0)
			first = list.get(0);
		
		String url = null;
		if(first != null)
			url = (String) first.get("url");
		else
			url = "No relevant training materials in search engine for this keyword yet";
		
		//System.out.println(url);
		return url;
	}
}
