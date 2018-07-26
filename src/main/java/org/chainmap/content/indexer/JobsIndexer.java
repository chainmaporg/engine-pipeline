package org.chainmap.content.indexer;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.chainmap.content.datatype.Jobs;

/**
 * Created by xingfeiy on 7/8/18.
 */
public class JobsIndexer extends AbstractContentIndexer<Jobs> {
    public JobsIndexer(SolrClient solr) {
        super(solr);
    }

    @Override
    protected void indexDoc(Jobs obj, SolrInputDocument doc) {
        doc.addField("company", obj.company);
        doc.addField("job_title", obj.title);
//        doc.addField("url", obj.url);
    }
}
