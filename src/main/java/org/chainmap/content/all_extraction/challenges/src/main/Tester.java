package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

import org.json.simple.JSONObject;

public class Tester {
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{	
		try {
			
			challenges.ChallengeExtractor cex = new challenges.ChallengeExtractor(args[0]);
			
			Timer timer = new Timer ();
			TimerTask t = new TimerTask () {
			    @Override
			    public void run () {
					try {
						cex.extract();
					} catch (Exception e) {
						e.printStackTrace();
					}
			    	System.out.println("Finished 1 cycle of challenge extraction. Quit now, or extraction will occur again in 24 hours");
			    }
			};
			
			timer.schedule (t, 0l, 1000*60*60*24);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
