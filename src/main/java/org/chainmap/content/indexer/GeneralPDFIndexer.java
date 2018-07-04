package org.chainmap.content.indexer;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.chainmap.content.datatype.GeneralPDFObj;

/**
 * Created by xingfeiy on 6/15/18.
 */
public class GeneralPDFIndexer extends AbstractContentIndexer<GeneralPDFObj> {
    public GeneralPDFIndexer(SolrClient solr) {
        super(solr);
    }

    @Override
    protected void indexDoc(GeneralPDFObj obj, SolrInputDocument doc) {

    }
}
