package org.chainmap.content.parser;

import org.chainmap.content.datatype.AbstractSearchObj;

import java.util.List;

/**
 * Created by xingfeiy on 6/15/18.
 */
public interface ObjParser<T extends AbstractSearchObj> {
    T parse(List<String> list);
}
