package org.chainmap.content.indexer;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.chainmap.content.datatype.CryptoEventInfo;

/**
 * Created by xingfeiy on 6/15/18.
 */
public class CryptoEventIndexer extends AbstractContentIndexer<CryptoEventInfo> {

    public CryptoEventIndexer(SolrClient solr) {
        super(solr);
    }

    @Override
    protected void indexDoc(CryptoEventInfo obj, SolrInputDocument doc) {
        doc.addField("event_name", obj.event_name);
        doc.addField("event_url", obj.event_url);
        doc.addField("event_date", obj.event_date);
        doc.addField("event_city", obj.event_city);
        doc.addField("event_country", obj.event_country);
        doc.addField("event_twitter", obj.event_twitter);
        doc.addField("event_contact_url", obj.event_contact_url);
        doc.addField("event_mail", obj.event_mail);
    }
}
