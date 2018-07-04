package org.chainmap.content.search;

import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by xingfeiy on 5/26/18.
 */
public class LanguageDetect {
    private static LanguageDetect instance = null;
    List<LanguageProfile> languageProfiles;

    TextObjectFactory textObjectFactory;

    LanguageDetector languageDetector;

    private LanguageDetect(){
        try {
            languageProfiles = new LanguageProfileReader().readAllBuiltIn();
            languageDetector = LanguageDetectorBuilder.create(NgramExtractors.standard())
                    .withProfiles(languageProfiles)
                    .build();
            textObjectFactory = CommonTextObjectFactories.forDetectingOnLargeText();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LanguageDetect getInstance() {
        if(instance == null) instance = new LanguageDetect();
        return instance;
    }

    public String getLang(String str) {
        TextObject textObject = textObjectFactory.forText(str);
        Set<LdLocale> res = languageDetector.detect(textObject).asSet();
        return res.isEmpty() ? "unknown" : res.iterator().next().getLanguage();
    }
}
