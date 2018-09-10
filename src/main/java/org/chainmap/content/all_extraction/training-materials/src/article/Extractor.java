package article;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

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
	
	public void extract()
	{
		try {
			
		Class.forName("com.mysql.jdbc.Driver") ;

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cmpdb", "dbuser", "telenav123");
		Statement stmt = conn.createStatement();
		String query = "Select * from materials";
		ResultSet rs = stmt.executeQuery(query);
		
		rs.first();
		
		while (rs.next()) {
			
			String url = rs.getString("link");
			
			if(url.contains("youtu"))
			{
				continue;
			}
					
			if(!isValid(url))
			{
				System.out.println("Faulty url: " + url + ", continuing to next result");
			} else {
						
				JSONObject jo = new JSONObject();
				
				jo.put("type", rs.getString("type"));
				jo.put("subtype", rs.getString("subtype"));
				jo.put("summary", rs.getString("description"));
				
				jo.put("title", rs.getString("name"));
				jo.put("category", "knowledge");
				jo.put("language", "english");
				
				String date = DateExtractor.extract(url);
				if(date.contains("extract"))
					jo.put("date", rs.getString("created"));
				else
					jo.put("date", DateExtractor.extract(url));
				jo.put("url", url);
				        
				try
				{
					String fullText = PDFTextExtractor.extract(url);
					
					if(fullText != null)
						jo.put("search_content", fullText);
					else
						jo.put("search_content", TextExtractor.extract(url));
	
					    
				    BufferedWriter out = new BufferedWriter(new FileWriter(folder + rs.getString("name").replaceAll("[^A-Za-z0-9]", "") + ".json"));
							 
					out.write(jo.toJSONString());
					out.close();
				}
				catch(Exception e)
				{
				    e.printStackTrace();
				}
				
				//System.out.println(jo.toJSONString());
				System.out.println("Finished extracting " + url);
			}
		}
		
		} catch (Exception e1) {
			e1.printStackTrace();
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
