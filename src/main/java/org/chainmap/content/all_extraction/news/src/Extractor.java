import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ListModel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

public class Extractor {

	private String folder;
	
	public Extractor(String where)
	{
		folder = where;
	}
	
	public static void main(String[] args){
		Extractor ex = new Extractor(args[1]);
		
		Timer timer = new Timer ();
		TimerTask t = new TimerTask () {
		    @Override
		    public void run () {
		    	ex.extract(Integer.parseInt(args[0]));
		    	System.out.println("Finished 1 cycle. Quit now, or extraction will occur again in 24 hours");
		    }
		};

		timer.schedule (t, 0l, 1000*60*60*24);
	}
	
	public void extract(int num)
	{
		List<String> urls = GoogleArticleFinder.getURLs(num);
			
		Set<String> s = new LinkedHashSet<>(urls);
			
		List<String> dedupedUrls = new ArrayList<>(s);
			
		for(int i = 0; i < num; i++)
		{
			String url = dedupedUrls.get(i);
				
			//String url = "";
				
			if(!isValid(url))
			{
				System.out.println("Faulty url: " + url + ", continuing to next result");
				num++;
			} else {
					
				JSONObject jo = new JSONObject();
			         
			       // putting data to JSONObject
			
				jo.put("order", i + "");
				jo.put("category", "news");
				jo.put("language", "english");
			    jo.put("title", TitleExtractor.extract(url));
			    jo.put("date", DateExtractor.extract(url));
			    jo.put("summary", SmmryExtractor.extract(url));
			    jo.put("url", url);
			        
			    try
			    {
				    
				    jo.put("search_content", TextExtractor.extract(url));
				    filepath = folder + TitleExtractor.extract(url).replaceAll("[^A-Za-z0-9]", "") + ".json";
				    
			    	BufferedWriter out = new BufferedWriter(new FileWriter(filepath));
						 
					out.write(jo.toJSONString());
					out.close();
					System.out.println("extracting to..."+filepath);
			    }
			    catch(Exception e)
			    {
			        e.printStackTrace();
			    }
					
				System.out.println(i + ". Finished extracting " + url);
			}
		}
	}
	
    public static boolean isValid(String url)
    {
        // Try creating a valid URL
        try {

			URL u = new URL (url);
			HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection (); 
			
			huc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			
			huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD"); 
			huc.connect () ; 
			int code = huc.getResponseCode() ;
			if(code-400 > 0)
			{
				System.out.println("HTTP Status Code: " + code);
				
				/*BufferedReader br = new BufferedReader(new InputStreamReader((huc.getErrorStream())));
				StringBuffer sb = new StringBuffer();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				System.out.println(sb);*/
				
				return false;
			}
			else
				return true;
        }
         
        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }

}
