package org.chainmap.content.indexer;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.chainmap.content.datatype.AbstractSearchObj;

import java.io.IOException;

/**
 * Created by xingfeiy on 6/15/18.
 */
public abstract class AbstractContentIndexer<T extends AbstractSearchObj> implements SolrIndexer<T> {
    private SolrClient solr = null;

    public AbstractContentIndexer(SolrClient solr) {
        this.solr = solr;
    }

    public void indexDoc(T obj) {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("category", obj.category);
        doc.addField("title", obj.title);
        doc.addField("search_content", obj.search_content);
        doc.addField("tags", StringUtils.join(obj.tags));
        doc.addField("summary", obj.summary);
        doc.addField("language", obj.language);
        doc.addField("url", obj.url);
        indexDoc(obj, doc);
        try {
            solr.add(doc);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void indexDoc(T obj, SolrInputDocument doc);
}
