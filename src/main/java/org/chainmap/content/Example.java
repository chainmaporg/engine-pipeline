package org.chainmap.content;

import org.chainmap.content.datatype.CryptoCompanyInfo;
import org.chainmap.content.extractor.CSVReader;
import org.chainmap.content.parser.CryptoCompanyParser;


/**
 * Created by xingfeiy on 4/13/18.
 */
public class Example {
    public static void main(String[] args) {
        CSVReader reader = new CSVReader("/Users/xingfeiy/githup/chainmap/chainmap/resources/cryptolist", new CryptoCompanyParser());
        while (reader.hasNext()) {
            CryptoCompanyInfo info = (CryptoCompanyInfo)reader.next();
            System.out.println(info.company);
        }
    }
}
