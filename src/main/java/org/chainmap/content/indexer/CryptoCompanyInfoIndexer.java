package org.chainmap.content.indexer;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.chainmap.content.datatype.CryptoCompanyInfo;


/**
 * Created by xingfeiy on 6/15/18.
 */
public class CryptoCompanyInfoIndexer extends AbstractContentIndexer<CryptoCompanyInfo>  {


    public CryptoCompanyInfoIndexer(SolrClient solr) {
        super(solr);
    }

    @Override
    protected void indexDoc(CryptoCompanyInfo obj, SolrInputDocument doc) {
        doc.addField("company", obj.company);
        doc.addField("company_url", obj.company_url);
        doc.addField("company_twitter", obj.company_twitter);
        doc.addField("company_mail", obj.company_email);
        doc.addField("company_blog", obj.blog_url);
        doc.addField("ceo", obj.ceo_name);
        doc.addField("ceo_mail", obj.ceo_email);
        doc.addField("ceo_twitter", obj.ceo_twitter);
    }
}
