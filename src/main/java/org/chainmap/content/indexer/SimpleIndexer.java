package org.chainmap.content.indexer;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.chainmap.content.extractor.PDFReader;
import org.chainmap.content.extractor.WebContentReader;
import org.chainmap.content.extractor.CSVReader;
import org.chainmap.content.datatype.AbstractSearchObj;
import org.chainmap.content.parser.*;

import java.io.IOException;

/**
 * Created by xingfeiy on 6/15/18.
 */
public class SimpleIndexer {

    private static SimpleIndexer instance = null;

    private SimpleIndexer(){}

    public static SimpleIndexer getInstance() {
        if(instance == null) {
            instance = new SimpleIndexer();
        }
        return instance;
    }

    public void indexWhitePaperPDFs(String path) {}

    public static <T extends AbstractSearchObj> void indexCryptoCSVInfos(String file, SolrIndexer<T> indexer, ObjParser<T> parser) {
        CSVReader reader = new CSVReader(file, parser);
        while (reader.hasNext()) {
            indexer.indexDoc((T)reader.next());
        }
    }


    public static void main(String[] args) throws IOException, SolrServerException {
//        String urlString = "http://107.182.235.108:8983/solr/chainmap";
        String urlString = "http://107.181.170.169:8983/solr/chainmap";
//        String urlString = "http://localhost:8983/solr/chainmap";
        final SolrClient solr = new HttpSolrClient.Builder(urlString).build();

        indexCryptoCSVInfos("/Users/xingfeiy/githup/chainmap/chainmap/resources/cryptolist/Crypto Companies.csv",
                new CryptoCompanyInfoIndexer(solr), new CryptoCompanyParser());

        indexCryptoCSVInfos("/Users/xingfeiy/githup/chainmap/chainmap/resources/cryptolist/Crypto Events.csv",
                new CryptoEventIndexer(solr), new CryptoEventParser());

        indexCryptoCSVInfos("/Users/xingfeiy/githup/chainmap/chainmap/resources/cryptolist/Crypto ICOs.csv",
                new CryptoICOIndexer(solr), new CryptoICOParser());

        WebContentReader webContentReader = new WebContentReader("/Users/xingfeiy/githup/chainmap/cmp/search-engine/resources/articles", "article");
        WebContentIndexer indexer = new WebContentIndexer(solr);
        while (webContentReader.hasNext()) {
            indexer.indexDoc(webContentReader.next());
        }

        webContentReader = new WebContentReader("/Users/xingfeiy/githup/chainmap/cmp/search-engine/resources/jobs", "job");
        indexer = new WebContentIndexer(solr);
        while (webContentReader.hasNext()) {
            indexer.indexDoc(webContentReader.next());
        }

        PDFReader pdfReader = new PDFReader("/Users/xingfeiy/githup/chainmap/chainmap/resources/whitepapers", "white_paper");
        GeneralPDFIndexer pdfIndexer = new GeneralPDFIndexer(solr);
        while (pdfReader.hasNext()) {
            pdfIndexer.indexDoc(pdfReader.next());
        }





        solr.commit();
    }
}
