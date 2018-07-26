package org.chainmap.content.indexer;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.chainmap.content.datatype.CryptoICOInfo;

/**
 * Created by xingfeiy on 6/16/18.
 */
public class CryptoICOIndexer extends AbstractContentIndexer<CryptoICOInfo> {
    public CryptoICOIndexer(SolrClient solr) {
        super(solr);
    }

    @Override
    protected void indexDoc(CryptoICOInfo obj, SolrInputDocument doc) {
        doc.addField("ico_name", obj.name);
//        doc.addField("url", obj.url);
        doc.addField("twitter", obj.twitter);
        doc.addField("email", obj.email);
        doc.addField("blog", obj.blog);
        doc.addField("contact_page", obj.contact_page);
        doc.addField("ceo", obj.ceo);
        doc.addField("ceo_twitter", obj.ceo_twitter);
        doc.addField("ceo_mail", obj.ceo_mail);
    }
}
