package com.runeventsparser.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by Николай on 30.01.2016.
 */
public class DneprRunParser {
    public String path = "http://dneprrun.dp.ua/sorevnovanija/te-zhe-na-manezhe-2016";

    public void connect() {
        Document doc = null;
        try {
            doc = Jsoup.connect(path).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element table = doc.select("table").get(1);
        Elements selectTr = table.select("tr");
        Elements rows = selectTr.select("li");
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).text().indexOf(":") == -1)
                continue;
            String resultData = rows.get(i).text();
            System.out.println(rows.get(i).text());
            parseResultRow(resultData);
        }
    }
        public void parseResultRow(String resultData){
            StringTokenizer token = new StringTokenizer (resultData);
            //String surname = token.nextToken();
            //String name = token.nextToken();
            System.out.println(token.countTokens());
           // if(token.countTokens()==4)
           // System.out.println("Surname: "+surname+" Name: "+name);


    }
}

