package getTags;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

public class TextExtractor {
	public static String extract(String add)
	{
		try
		{
		    Document doc = Jsoup.connect(add).get();
		    String html = doc.toString();
		
			String text = ArticleExtractor.INSTANCE.getText(html);
			return text;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Exception when extracting full text";
	}
}
