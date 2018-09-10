package getTags;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TagsExtractor {
	
	/*public static void main(String[] args) throws ClassNotFoundException, MalformedURLException, SQLException
	{
		List<String> tags = extract("https://indeed.com/rc/clk?jk=28f884aa7cc3bc20&fccid=a4e4e2eaf26690c9&vjs=3");
	}*/
	
	private static boolean isWord(String word, String textSnippet)
	{
		String wordPeriod = word + ".";
		String wordQues = word + "?";
		String wordExc = word + "!";
		String wordComma = word + ",";
		
		return word.equals(textSnippet) || wordPeriod.equals(textSnippet) || 
				wordQues.equals(textSnippet) || wordExc.equals(textSnippet) || wordComma.equals(textSnippet);
	}
	
	static int count(String str, String word) 
	{
	    // split the string by spaces in a
	    String a[] = str.split(" ");
	 
	    // search for pattern in a
	    int count = 0;
	    for (int i = 0; i < a.length; i++) 
	    {
	    // if match found increase count
	    if (isWord(word, a[i]))
	        count++;
	    }
	 
	    return count;
	}
	
	public static List<String> extract(String url) throws ClassNotFoundException, SQLException, MalformedURLException
	{
		List<String> result = new ArrayList<String>();
		
		
		
		Class.forName("com.mysql.jdbc.Driver") ;

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cmpdb", "dbuser", "telenav123");
		Statement stmt = conn.createStatement();
		String query = "Select * from keywords";
		ResultSet rs = stmt.executeQuery(query);
		
		rs.first();
		
		Map<String, Integer> keywordCount = new HashMap<String, Integer>();
		
		String text = TextExtractor.extract(url);

		//System.out.println(text);
		
		text = text.toLowerCase();
		
		do
		{
			String keyword = rs.getString("keyword");
			//System.out.println(keyword);
			int count = count(text, keyword.toLowerCase());
			
			keywordCount.put(keyword, count);
		}
		while(rs.next());
		
		List<Obj> sorted = sortByValue(keywordCount);
		
		for(int i = 0; i < 5; i++)
		{
			Obj o = sorted.get(i);
			if(o.val < 1)
			{
				//System.out.println(o.val + " " + o.key);
				break;
			}
			else
			{
				//System.out.println(o.val + " " + o.key);
				result.add(o.key);
			}
		}
		
		//System.out.println(result.size());
		
		return result;
	}
	
	public static List<Obj> sortByValue(Map<String, Integer> mp){

	    ArrayList<Obj> arr=new ArrayList<Obj>();
	    for(String z:mp.keySet())//Make an object and store your map into the arrayList
	    {

	        Obj o=new Obj(z,mp.get(z));
	        arr.add(o);
	    }
	    //System.out.println(arr);//Unsorted
	    Collections.sort(arr);// This sorts based on the conditions you coded in the compareTo function.
	    
	    return arr;
	    //System.out.println(arr);//Sorted
	}
}

class Obj implements Comparable<Obj>{
    public String key;
    public Integer val;
    Obj(String key, Integer val)
    {
    this.key=key;
    this.val=val;
    }
    public int compareTo(Obj o)
    {
	 if(this.val - o.val > 0)
		 return -1;
	 else if(this.val == o.val)
		 return 0;
	 else
		 return 1;
    }
}


