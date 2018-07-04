package org.chainmap.content.indexer;

import org.chainmap.content.datatype.AbstractSearchObj;

/**
 * Created by xingfeiy on 6/15/18.
 */
public interface SolrIndexer<T extends AbstractSearchObj> {
    public void indexDoc(T obj);
}
