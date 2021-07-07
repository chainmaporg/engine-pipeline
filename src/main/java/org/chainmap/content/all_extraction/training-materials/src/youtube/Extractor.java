package youtube;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import article.DateExtractor;
import article.TitleExtractor;

public class Extractor {
	
	private String folder;
	
	public Extractor(String where)
	{
		folder = where;
	}
	
	private static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	public void extract() throws ClientProtocolException, IOException, ParseException, SQLException, ClassNotFoundException
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
        try 
        {
        	
        	Class.forName("com.mysql.jdbc.Driver") ;

    		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cmpdb", "dbuser", "telenav123");
    		Statement stmt = conn.createStatement();
    		String query = "Select * from materials";
    		ResultSet rs = stmt.executeQuery(query);
    		
    		rs.first();
    		
    		while (rs.next()) 
    		{
    		
	    		String url = rs.getString("link");
	    		
	    		if(!url.contains("youtu"))
	    		{
	    			continue;
	    		}
	    		
	    		if(!isValid(url))
				{
					System.out.println("Faulty url: " + url + ", continuing to next result");
				}
	    		else
	    		{
		    		String id = null;
		    		
		    		String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
		
		            Pattern compiledPattern = Pattern.compile(pattern);
		            Matcher matcher = compiledPattern.matcher(url); //url is youtube url for which you want to extract the id.
		            if (matcher.find()) {
		                 id = matcher.group();
		            }
		            
		    		JSONObject jo = new JSONObject();
			        
		    		jo.put("title", rs.getString("name"));
		    		jo.put("type", rs.getString("type"));
					jo.put("subtype", rs.getString("subtype"));
					
					jo.put("category", "knowledge");
					jo.put("language", "english");
					jo.put("url", url);
					
					if(id == null)
					{
						jo.put("date", jo.put("date", rs.getString("created")));
						jo.put("search_content", "invalid video url");
					}
					else
					{
			        	HttpGet request = new HttpGet("https://www.googleapis.com/youtube/v3/videos?part=snippet&id="
			            		+ id + "&key=AIzaSyAXkF7wzvzx-2QBF9Z_l52N1ZpCddC0LIM");
			
			            CloseableHttpResponse response;
			            
			            response = httpclient.execute(request);
			            HttpEntity entity= response.getEntity();
			            
			            JSONParser parser = new JSONParser();
			            
			            try {
			                
			            	InputStream in = entity.getContent();
			                
			                String jsonString = convertStreamToString(in);
			
			                Object obj = parser.parse(jsonString);
			                
			            	JSONObject result = (JSONObject) obj;
			            	
			                EntityUtils.consume(entity);
			                
			                JSONArray items = (JSONArray) result.get("items");
			                
			                JSONObject zero = (JSONObject) items.get(0);
			                
			                JSONObject snippet = (JSONObject) zero.get("snippet");
			                
			                JSONArray tags = (JSONArray) snippet.get("tags");
			                
			                String allTags = " ";
			                
			                if(tags != null)
			                {
				                for(int i = 0; i < tags.size(); i++)
				                {
				                	allTags += (tags.get(i) + " ");
				                }
			                }
			                
						    jo.put("date", snippet.get("publishedAt"));
						    jo.put("search_content", snippet.get("description") + allTags);
						    jo.put("summary", snippet.get("description"));
						    
						    BufferedWriter out = new BufferedWriter(new FileWriter(folder + rs.getString("name").replaceAll("[^A-Za-z0-9]", "") + ".json"));
							 
							out.write(jo.toJSONString());
							out.close();
							
						    //System.out.println(jo.toJSONString());
						    System.out.println("Finished extracting " + url);
		                
			            } 
				        finally 
				        {
			                response.close();
			            }
					}	
	    		}
    		}
	    } 
        finally 
        {
	        httpclient.close();
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
       
        catch (Exception e) {
            return false;
        }
    }
}
