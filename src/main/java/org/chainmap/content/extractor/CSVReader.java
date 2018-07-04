package org.chainmap.content.extractor;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.chainmap.content.datatype.AbstractSearchObj;
import org.chainmap.content.parser.ObjParser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xingfeiy on 6/15/18.
 */
public class CSVReader implements FileReader<AbstractSearchObj>{
    private String file = StringUtils.EMPTY;

    private String separator = ",";

    private ObjParser parser = null;

    private List<AbstractSearchObj> container = new ArrayList<>();;

    private int curIndex = 0;

    public CSVReader(String file, ObjParser parser) {
        this.file = file;
        this.parser = parser;
        parse();
    }

    public CSVReader(String file, String separator, ObjParser parser) {
        this.file = file;
        this.separator = separator;
        this.parser = parser;
        parse();
    }

    private void parse() {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(this.file));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            for (CSVRecord csvRecord : csvParser) {
                List<String> tokens = new ArrayList<>();
                for(int i = 0; i < csvRecord.size(); i++) tokens.add(csvRecord.get(i));
                AbstractSearchObj obj = this.parser.parse(tokens);
                this.container.add(obj);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean hasNext() {
        return this.curIndex < this.container.size();
    }

    public AbstractSearchObj next() {
        return this.container.get(curIndex++);
    }
}
