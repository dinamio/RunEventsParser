package com.runeventsparser.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zOpa on 16.03.2016.
 */
public class CreateFile {
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
