package org.chainmap.content.extractor;

import org.apache.commons.lang.StringUtils;
import org.chainmap.content.datatype.WebContent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xingfeiy on 7/5/18.
 */
public class WebContentReader implements CMFileReader<WebContent> {
    protected String path = "";

    protected String category = "";

    protected List<WebContent> container = new ArrayList<>();;

    protected int curIndex = 0;


    public WebContentReader(String path) {
        this.path = path;
        parse();
    }

    public WebContentReader(String path, String category) {
        this.path = path;
        this.category = category;
        parse();
    }

    protected void parse() {
        Path path = Paths.get(this.path);
        if (Files.isDirectory(path)) {
            //Iterate directory
            try {
                Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if(file.toFile().getAbsolutePath().endsWith(".txt") || file.toFile().getAbsolutePath().endsWith(".TXT")) {
                            WebContent obj = parse(file);
                            if(obj != null) container.add(obj);

                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //Index this file
            container.add(parse(path));
        }
    }

    protected WebContent parse(Path file) {
        BufferedReader br;
        WebContent webContent = new WebContent();
        try {
            br = new BufferedReader(new FileReader(file.toFile()));
            StringBuilder content = new StringBuilder();
            String link = "";
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().startsWith("Original link")) {
                    link = line.substring(line.indexOf("http"));
                } else {
                    content.append(line).append(" ");
                }
            }


            webContent.url = link;
            webContent.category = category;
            webContent.search_content = content.toString().trim();
            webContent.summary = webContent.search_content.substring(0, Math.min(300, webContent.search_content.length()));
            webContent.title = StringUtils.removeEnd(file.toFile().getName(), ".txt");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return webContent;
    }

    @Override
    public boolean hasNext() {
        return this.curIndex < this.container.size();
    }

    @Override
    public WebContent next() {
        return this.container.get(curIndex++);
    }
}
