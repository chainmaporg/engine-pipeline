package org.chainmap.content.parser;


import org.chainmap.content.datatype.CryptoCompanyInfo;

import java.util.List;

/**
 * Data definition(csv):
 * company,url,company_twitter,contact_page,blog,email,email_verification,ceo_name,ceo_twitter,ceo_email,ceo_email_verification
 * Created by xingfeiy on 6/15/18.
 */
public class CryptoCompanyParser implements ObjParser<CryptoCompanyInfo> {
    @Override
    public CryptoCompanyInfo parse(List<String> list) {
        CryptoCompanyInfo info = new CryptoCompanyInfo();
        if(list == null || list.size() < 11) return info;
        int index = 0;
        info.company = list.get(index++);
        info.company_url = list.get(index++);
        info.company_twitter = list.get(index++);
        info.contact_url = list.get(index++);
        info.blog_url = list.get(index++);
        info.company_email = list.get(index++);
        info.company_email_verification = list.get(index++);
        info.ceo_name = list.get(index++);
        info.ceo_twitter = list.get(index++);
        info.ceo_email = list.get(index++);
        info.ceo_email_verification = list.get(index++);

        info.category = "company";
        info.title = info.company;
        info.search_content = new StringBuilder().append(info.company).append(" ").append(info.ceo_name).toString().trim();
        info.summary = new StringBuilder().append(info.company).append(" found by ").append(info.ceo_name).toString().trim();
        return info;
    }
}
