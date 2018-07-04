package org.chainmap.content.indexer;

import org.apache.solr.client.solrj.SolrClient;

/**
 * Created by xingfeiy on 7/4/18.
 */
public class IndexerFactory {
    private static IndexerFactory factory = null;

    private IndexerFactory() {}

    public static IndexerFactory getFactory() {
        if(factory == null) {
            factory = new IndexerFactory();
        }
        return factory;
    }

    public SolrIndexer getIndexer(RawDataType type, SolrClient solr) {
        switch (type) {
            case WHITE_PAPER:
                return new GeneralPDFIndexer(solr);
            case CRYPTO_COMPANY:
                return new CryptoCompanyInfoIndexer(solr);
            case CRYPTO_EVENT:
                return new CryptoEventIndexer(solr);
            case CRYPTO_ICO:
                new CryptoICOIndexer(solr);
            default:
                    return null;
        }
    }
}
