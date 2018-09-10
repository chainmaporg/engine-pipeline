import java.net.MalformedURLException;
import java.net.URL;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

public class TextExtractor {
	public static String extract(String add) throws MalformedURLException
	{
		try
		{
			URL url = new URL(add);
			String text = ArticleExtractor.INSTANCE.getText(url);
			return text;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Exception when extracting full text";
	}
}
