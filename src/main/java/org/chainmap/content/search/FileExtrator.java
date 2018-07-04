package org.chainmap.content.search;

import org.apache.commons.exec.util.StringUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xingfeiy on 5/16/18.
 */
public class FileExtrator {
    public static void main(String[] args) {
        File file = new File("/Users/xingfeiy/Documents/white-papers-thru-mar-2018 (Autosaved).csv");
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;
            Map<String, String> map = new HashMap<>();
            while ((line = br.readLine()) != null) {
                String[] tokens = StringUtils.split(line, ",");
                if(tokens.length == 2 && tokens[1].endsWith("pdf")) {
                    map.put(tokens[0], tokens[1]);
                }

            }

            br.close();

            download(map);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void download(Map<String, String> map) {
        int count = 0;
        List<String> wget = new ArrayList<>();
        List<String> mv = new ArrayList<>();
        List<String> command = new ArrayList<>();
        map.clear();
        map.put("Arkadia", "https://www.arkadialending.com/images/docs/Arkadia-Lending-Whitepaper.pdf");
        map.put("Arkadia", "https://www.arkadialending.com/images/docs/Arkadia-Lending-Whitepaper.pdf");
        for(Map.Entry<String, String> entry : map.entrySet()) {
            URL url = null;
            try {
                url = new URL(entry.getValue());
                InputStream in = url.openStream();
                Files.copy(in, Paths.get("./whitepapers/" + entry.getKey().trim() + ".pdf"), StandardCopyOption.REPLACE_EXISTING);
                count++;
//                wget.add("wget " + entry.getValue());
//                System.out.println("wget " + entry.getValue() + ";");
//                command.add("wget " + entry.getValue() + ";");
//                String[] fileNmae = entry.getValue().split("/");
//                mv.add("mv " + fileNmae[fileNmae.length - 1] + " ../rename/" + entry.getKey());
//                System.out.println("mv " + fileNmae[fileNmae.length - 1] + " " + entry.getKey() + ".pdf;");
//                command.add("mv " + fileNmae[fileNmae.length - 1] + " " + entry.getKey() + ".pdf;");
                in.close();
            } catch (MalformedURLException e) {
                System.out.println("Reason1 : Can't download " + entry.getValue());
                continue;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Reason2: Can't download " + entry.getValue());
                continue;
            }
        }

        System.out.println("Total Links: " + map.size() + ". Downloaded: " + count);
        Path file = Paths.get("wget.txt");
        Path fileMv = Paths.get("mv.txt");
        Path fileCommand = Paths.get("command.txt");
        try {
            Files.write(file, wget, Charset.forName("UTF-8"));
            Files.write(fileMv, mv, Charset.forName("UTF-8"));
            Files.write(fileCommand, command, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
