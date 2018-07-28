package org.chainmap.content.extractor;

import org.apache.commons.lang.StringUtils;
import org.chainmap.content.datatype.WebContent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by xingfeiy on 7/25/18.
 */
public class WebNewReader extends WebContentReader {

    public WebNewReader(String path) {
        super(path);
    }

    public WebNewReader(String path, String category) {
        super(path, category);
    }

    protected WebContent parse(Path file) {
        BufferedReader br;
        WebContent webContent = new WebContent();
        try {
            br = new BufferedReader(new FileReader(file.toFile()));
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().startsWith("date:")) {
                    //2018-07-20 00:00:00
//                    webContent.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(br.readLine());
                }

                if(line.trim().startsWith("summary:")) {
                    StringBuilder sb = new StringBuilder();
                    while ((line = br.readLine()) != null && !line.trim().startsWith("full text:")) {
                        sb.append(line);
                        webContent.summary = sb.toString().trim();
                    }
                }

                if(line.trim().startsWith("full text:")) {
                    StringBuilder sb = new StringBuilder();
                    while ((line = br.readLine()) != null && !line.trim().startsWith("url:")) {
                        sb.append(line);
                    }
                    webContent.search_content = sb.toString().trim();
                }
                if(line.trim().startsWith("url:")) {
                    webContent.url = br.readLine();
                    webContent.title = webContent.url;
                }
            }


            webContent.category = category;
//            webContent.title = StringUtils.removeEnd(file.toFile().getName(), ".txt");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (ParseException e) {
//            e.printStackTrace();
//        }
        return webContent;
    }
}
