A Java wrapper for retrieving Google search results in Java. By default "https://www.google.com/search?" interface is used, so no API keys are needed.

Usage:
```java
	SearchQuery query = new SearchQuery.Builder("bunnies")
    				    .site("stackoverflow.com")
        				.numResults(10).build();
    SearchResult result = new GoogleWebSearch().search(query);
    System.out.println(result.getUrls());
```

Default search parameters can be overriden by using an alternative constructor:

```java
	import com.afedulov.search.GoogleWebSearch.SearchConfig;
	...
	SearchQuery query = new SearchQuery.Builder("bunnies aus Deutschland")
    				    .site("stackoverflow.com")
        				.numResults(10).build();
    SearchConfig config = new SearchConfig();
    config.setGOOGLE_SEARCH_URL_PREFIX("https://www.google.de/search?");
    SearchResult result = new GoogleWebSearch(config).search(query);
    System.out.println(result.getUrls());
```    

Warning from Google Terms of Service:
"Don’t misuse our Services. For example, don’t interfere with our Services or try to access them using a method other than the interface and the instructions that we provide.". Is "https://www.google.com/search?" an interface that Google provides? I'll leave it up to you to interpret and decide. Use only if you think it is appropriate and at your own risk.

This library is not intended for quering in highly concurrent and/or frequent manner. Doing so will most probably lead to blocking by Google with the "Unusual traffic from your computer network" message.

