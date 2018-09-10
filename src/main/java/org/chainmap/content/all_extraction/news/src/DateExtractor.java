
import java.io.*;
import java.nio.file.Files;
import java.util.Map;

public class DateExtractor {
	 
	 public static String extract(String url)
	 {
		 try{
		  
			 String prg = "import articleDateExtractor\n"
			 		+ "d = articleDateExtractor.extractArticlePublishedDate(\"" + url + "\")\n"
					+ "print(d)";
			 BufferedWriter out = new BufferedWriter(new FileWriter("test.py"));
			 
			 out.write(prg);
			 out.close();
			  
			 ProcessBuilder pb = new ProcessBuilder("python","test.py");
			 Process p = pb.start();
			  
			 BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			 String ret = in.readLine();
			 ret = in.readLine();
			 
			 String regex = "\\d";
			 
			 if(ret.substring(0, 1).matches(regex))
				 return ret.substring(0, 10);
			 else
				 return "Cannot extract date";
			 
		 }
		 catch(Exception e){
			 e.printStackTrace();;
		 }
		 
		 return "Cannot extract date";
	 }
}

