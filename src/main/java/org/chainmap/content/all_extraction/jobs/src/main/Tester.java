package main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.solr.client.solrj.SolrServerException;

import jobs.JobExtractor;

public class Tester {

	public static void main(String[] args) {
		try {
			//System.out.println(getTags.TagsExtractor.extract("https://www.indeed.com/viewjob?jk=90dff0edef76f782&tk=1cllotemn1c1d0vc&from=vjnewtab"));
			JobExtractor job = new JobExtractor(args[0]);
			
			Timer timer = new Timer ();
			TimerTask t = new TimerTask () {
			    @Override
			    public void run () {
					try {
						job.extractToCSV();
						job.extractToJSON();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	System.out.println("Finished 1 cycle of job extraction. Quit now, or extraction will occur again in 24 hours");
			    }
			};

			timer.schedule (t, 0l, 1000*60*60*24);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
