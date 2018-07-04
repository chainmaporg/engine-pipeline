package org.chainmap.content.parser;

import org.chainmap.content.datatype.CryptoICOInfo;

import java.util.List;

/**
 * Created by xingfeiy on 6/16/18.
 */
public class CryptoICOParser implements ObjParser<CryptoICOInfo> {
    @Override
    public CryptoICOInfo parse(List<String> list) {
        CryptoICOInfo info = new CryptoICOInfo();
        if(list == null || list.size() < 1) return info;
        int index = 0;
        info.name = list.get(index++);
        info.url = list.get(index++);
        info.twitter = list.get(index++);
        info.email = list.get(index++);
        info.blog = list.get(index++);
        info.contact_page = list.get(index++);
        info.ceo = list.get(index++);
        info.ceo_twitter = list.get(index++);
        info.ceo_mail = list.get(index++);

        info.category = "ICO";
        info.title = info.name;
        info.search_content = new StringBuilder().append(info.name).append(" ").append(info.ceo).toString();
        info.summary = new StringBuilder().append("ICO " + info.name).toString();
        return info;
    }
}
