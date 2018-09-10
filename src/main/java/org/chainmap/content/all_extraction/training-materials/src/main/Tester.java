package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.parser.ParseException;

public class Tester {
	public static void main(String[] args)
	{
		try {
			
			youtube.Extractor uex = new youtube.Extractor(args[0]);
			article.Extractor aex = new article.Extractor(args[0]);
			
			Timer timer = new Timer ();
			TimerTask t = new TimerTask () {
			    @Override
			    public void run () {
					try {
						uex.extract();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					aex.extract();
			    	System.out.println("Finished 1 cycle of knowledge extraction. Quit now, or extraction will occur again in 24 hours");
			    }
			};
			
			timer.schedule (t, 0l, 1000*60*60*24);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
