package article;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TitleExtractor {
	public static String extract(String url)
	{
		try
		{
			Document doc = Jsoup.connect(url).get();
			String title = doc.title();
	        
			title = title.replaceAll("\u2014", "-");
			
			if(title.contains("|"))
			{
				if(getLongest(title.split("[|]")) == 0)
					title = title.split("[|]")[0];
			}
			else if(title.contains(" -"))
			{
				if(getLongest(title.split(" -")) == 0)
					title = title.split(" -")[0];
			}
			
	        return title.trim();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Exception occurred when extracting title";
	}
	
	private static int getLongest(String[] arr)
	{
		int longest = 0;
		int index = -1;
		for(int i = 0; i < arr.length; i++)
		{
			if(arr[i].length() > longest)
			{
				longest = arr[i].length();
				index = i;
			}
		}
		
		return index;
	}
}
