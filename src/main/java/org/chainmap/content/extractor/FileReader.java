package org.chainmap.content.extractor;

import org.chainmap.content.datatype.AbstractSearchObj;

/**
 * Created by xingfeiy on 6/15/18.
 */
public interface FileReader<T extends AbstractSearchObj> {
    boolean hasNext();

    T next();
}
