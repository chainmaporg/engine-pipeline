package challenges;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;

public class ChallengeExtractor {
	private String folder;
	
	public ChallengeExtractor(String where)
	{
		folder = where;
	}
	
	public void extract() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cmpdb", "dbuser", "telenav123");
		Statement stmt = conn.createStatement();
		String query = "Select * from challenge";
		ResultSet rs = stmt.executeQuery(query);
		
		rs.first();
		
		JSONObject jo = new JSONObject();
		
		while (rs.next()) {
			try
			{
				jo.put("challenge_id", rs.getInt("challenge_id"));
				
				jo.put("post_user_id", rs.getInt("post_user_id"));
				
				jo.put("title", rs.getString("title"));
				
				jo.put("search_content", rs.getString("title") + "\n" + rs.getString("description"));
				
				jo.put("upvote_count", rs.getInt("upvote_count"));
				
				jo.put("downvote_count", rs.getInt("downvote_count"));
				
				jo.put("view_count", rs.getInt("view_count"));
				
				jo.put("category", "challenges");
				
				jo.put("question_category", rs.getString("category"));
				
				jo.put("level", rs.getString("level"));
				
				jo.put("date", rs.getString("posting_date"));
				
				jo.put("is_closed", rs.getBoolean("is_closed"));
				
				jo.put("language", "english");
				
				jo.put("url", "http://chainmap.org/getChallengebyID/" + rs.getInt("challenge_id"));
				
				BufferedWriter out = new BufferedWriter(new FileWriter(folder + rs.getString("title").replaceAll("[^A-Za-z0-9]", "") + ".json"));
				 
				out.write(jo.toJSONString());
				out.close();
				
				System.out.println("Finished extracting" + " http://chainmap.org/getChallengebyID/" + rs.getInt("challenge_id"));
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
