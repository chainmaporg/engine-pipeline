package jobs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.opencsv.CSVReader;

public class JobExtractor {
	
	private String folder;
	public JobExtractor(String where)
	{
		folder = where;
	}
	
	public void extractToCSV() throws IOException, InterruptedException
	{
		
		 ProcessBuilder pb = new ProcessBuilder("python","testscrape.py");
		 //System.out.println(pb.directory());
		 pb.directory(null);
		 pb.inheritIO();
		 //System.out.println(pb.directory());
		 Process p = pb.start();
		 
		 while(p.isAlive())
		 {
			 Thread.sleep(1000);
		 }
		 
		 System.out.println("CSV FILE FINISHED");
	}
	
	public void extractToJSON() throws ClassNotFoundException, SQLException, SolrServerException
	{
		System.out.println("BEGINNING SECOND METHOD");
		String csvFile = "scraperesults.csv";

        CSVReader reader = null;
        
        JSONObject jo = new JSONObject();
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            line = reader.readNext();
            if(line != null)
            {
	            while ((line = reader.readNext()) != null) {
	            	if(line[2].contains("pagead"))
	            		continue;
	            	
	            	String url = "https://indeed.com" + line[2];
	            	String dateString = line[1];
	            	String date = DateCalculator.calculate(dateString);
	            	String summary = line[7];
	            	String company = line[5];
	            	String city = line[3];
	            	String location = line[6];
	            	String title = line[4];
	            	
	            	if(title.contains("Error"))
	            	{
	            		continue;
	            	}
	            	
	            	//System.out.println(date);
	            	
	            	String text = getTags.TextExtractor.extract(url);
	            	List<String> tags = getTags.TagsExtractor.extract(url);
	            	//System.out.println(tags.size());
	            	
	                jo.put("url", url);
	                jo.put("search_content", text);
	                jo.put("date", date);
	                jo.put("category", "job_postings");
	                
	                jo.put("summary", summary);
	                jo.put("city", city);
	                jo.put("location", location);
	                jo.put("company", company);
	                jo.put("title", title);
	                
	                System.out.println(title);
	                
	                JSONArray tagArray = new JSONArray();
	                JSONArray materials = new JSONArray();
	                
	                for(int i = 0; i < tags.size(); i++)
	                {
	                	String tag = tags.get(i);
	                	
	                	System.out.println(tag);
	                	tagArray.add(tag);
	                	
	                	materials.add(getKnowledge.KnowledgeExtractor.extract(tag));
	                	
	                }
	                
	                jo.put("tags", tagArray);
	                jo.put("knowledge", materials);
	                
	                try {
	                	BufferedWriter out = new BufferedWriter(new FileWriter(folder + title.replaceAll("[^A-Za-z0-9]", "") + ".json"));
						 
						out.write(jo.toJSONString());
						out.close();
	                }
	                catch(Exception e)
	                {
	                	e.printStackTrace();
	                }
	            }
            }
            else
            {
            	System.out.println("Empty csv file!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
