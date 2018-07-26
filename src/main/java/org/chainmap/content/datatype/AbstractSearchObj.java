package org.chainmap.content.datatype;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xingfeiy on 6/13/18.
 */
public abstract class AbstractSearchObj {

    public String category = "UnKnown";

    public String title = StringUtils.EMPTY;

    public String search_content = StringUtils.EMPTY;

    public List<String> tags = new ArrayList<>();

    public String summary = "";

    public String language = "eng";

    public String url = "none";

    public long id = 0l;

}
