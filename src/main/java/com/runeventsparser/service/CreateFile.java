package com.runeventsparser.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zOpa on 16.03.2016.
 */
public class CreateFile {

    public Document checksAndCreatesFile (String url, String pathOffline) throws IOException {
        Document doc = null;
        if((new File(pathOffline).exists())) {
          doc = Jsoup.parse(new File(pathOffline),"utf-8");
        }else {
            try {
                doc = Jsoup.connect(url).get();
                createFileForOfflineParser(url, pathOffline);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return doc;
    }

    public void createFileForOfflineParser(String url, String pathFile){
        try {
            Document doc = Jsoup.connect(url).get();
            BufferedWriter out = new BufferedWriter(new PrintWriter(pathFile,"utf-8"));
            out.write(String.valueOf(doc));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
