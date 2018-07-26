package org.chainmap.content.indexer;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.chainmap.content.datatype.WebContent;

/**
 * Created by xingfeiy on 7/5/18.
 */
public class WebContentIndexer extends AbstractContentIndexer<WebContent> {
    public WebContentIndexer(SolrClient solr) {
        super(solr);
    }

    @Override
    protected void indexDoc(WebContent obj, SolrInputDocument doc) {
//        doc.addField("url", obj.url);
    }
}
