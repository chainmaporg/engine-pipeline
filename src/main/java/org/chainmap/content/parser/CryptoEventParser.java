package org.chainmap.content.parser;

import org.chainmap.content.datatype.CryptoEventInfo;

import java.util.List;

/**
 * Created by xingfeiy on 6/15/18.
 */
public class CryptoEventParser implements ObjParser<CryptoEventInfo> {
    @Override
    public CryptoEventInfo parse(List<String> list) {
        CryptoEventInfo info = new CryptoEventInfo();
        if(list == null || list.size() < 1) return info;
        int index = 0;
        info.event_name = list.get(index++);
        info.event_url = list.get(index++);
        info.event_date = list.get(index++);
        info.event_city = list.get(index++);
        info.event_country = list.get(index++);
        info.event_twitter = list.get(index++);
        info.event_contact_url = list.get(index++);
        info.event_mail = list.get(index++);

        info.category = "event";
        info.title = info.event_name;
        info.search_content = info.event_name;
        info.summary = new StringBuilder().append(info.event_name).append(", ").
                append(info.event_city + ", " + info.event_country).append(", ").append(info.event_date).toString();
        return info;
    }
}
