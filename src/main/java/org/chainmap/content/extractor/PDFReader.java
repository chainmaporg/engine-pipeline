package org.chainmap.content.extractor;

import org.apache.commons.lang.StringUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.chainmap.content.datatype.GeneralPDFObj;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xingfeiy on 6/15/18.
 */
public class PDFReader implements FileReader<GeneralPDFObj> {
    private String path = StringUtils.EMPTY;

    private List<GeneralPDFObj> container = new ArrayList<>();

    private int curIndex = 0;

    private String category = "PDF";

    public PDFReader(String path, String category) {
        this.path = path;
        this.category = category;
        parse();
    }

    private void parse() {
        Path path = Paths.get(this.path);
        if (Files.isDirectory(path)) {
            //Iterate directory
            try {
                Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if(file.toFile().getAbsolutePath().endsWith(".pdf") || file.toFile().getAbsolutePath().endsWith(".PDF")) {
                            GeneralPDFObj obj = parse(file);
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

    private GeneralPDFObj parse(Path file) {
        System.out.println("Indexing PDF: " + file.toFile().getAbsolutePath());
        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        GeneralPDFObj obj = new GeneralPDFObj();
        try {
            FileInputStream inputstream = new FileInputStream(file.toFile());
            ParseContext pcontext = new ParseContext();

            PDFParser pdfparser = new PDFParser();
            pdfparser.parse(inputstream, handler, metadata,pcontext);
            obj.search_content = handler.toString();
            obj.title = file.getFileName().toString();
            obj.category = this.category;
            obj.summary = summary(obj.search_content);

        } catch (Exception ex) {
            System.out.println("Getting exception when parsing " + file.toFile().getAbsolutePath());
            ex.printStackTrace();
            return null;
        }
        return obj;
    }

    private String summary(String content) {
        String summary = content.substring(0, Math.min(1000, content.length()));
        while (summary.contains("\n")) summary = StringUtils.replace(summary, "\n", ".");
        while (summary.contains("\r")) summary = StringUtils.replace(summary, "\r", ".");
        while (summary.contains("\\s\\s")) summary = StringUtils.replace(summary, "\\s\\s", "\\s");
        while (summary.contains("  ")) summary = StringUtils.replace(summary, "  ", " ").trim();
        while (summary.startsWith("\\s")) summary = StringUtils.removeStart(summary, "\\s");
        while (summary.startsWith(".")) summary = StringUtils.removeStart(summary, ".").trim();
        while (summary.contains("\\s.")) summary = StringUtils.replace(summary, "\\s.", ".");
        while (summary.contains("..")) summary = StringUtils.replace(summary, "..", ".");
        while (summary.length() > 0 && !Character.isLetterOrDigit(summary.charAt(0))) summary = summary.substring(1, summary.length());
        while (summary.contains(". .")) summary = StringUtils.replace(summary, ". .", ".");
        return summary.trim().substring(0, Math.min(200, summary.trim().length())) + "...";
    }

    @Override
    public boolean hasNext() {
        return this.curIndex < container.size();
    }

    @Override
    public GeneralPDFObj next() {
        return this.container.get(curIndex++);
    }
}
