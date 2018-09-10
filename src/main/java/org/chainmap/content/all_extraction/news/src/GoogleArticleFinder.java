import java.util.List;

import com.afedulov.search.GoogleWebSearch;
import com.afedulov.search.GoogleWebSearch.SearchConfig;
import com.afedulov.search.SearchQuery;
import com.afedulov.search.SearchResult;

public class GoogleArticleFinder {

	public static List<String> getURLs(int num)
	{
		SearchQuery query = new SearchQuery.Builder("blockchain")
			    //.site("")
				.numResults(num * 3).build();
		SearchConfig config = new SearchConfig();
		config.setGOOGLE_SEARCH_URL_PREFIX("https://www.google.com/search?hl=en&");
		SearchResult result = new GoogleWebSearch(config).search(query);
		return result.getUrls();
		
	}
}
