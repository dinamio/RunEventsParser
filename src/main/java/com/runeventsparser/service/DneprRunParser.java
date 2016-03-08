package com.runeventsparser.service;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.google.gson.Gson;
import com.runeventsparser.bom.Result;
import com.runeventsparser.bom.Runner;
import com.runeventsparser.bom.Sex;
import com.runeventsparser.bom.Time;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



public class DneprRunParser {

        String url = "http://dneprrun.dp.ua/sorevnovanija/te-zhe-na-manezhe-2016";
        String paserFileDneprRun = "src/main/java/com/runeventsparser/Files/HtmlFiles/DneprRun.html";

    public List<Runner> parse (String url) throws IOException {
        Document doc;
        List<Runner> runners = new LinkedList<Runner>();
        doc = Jsoup.connect(url).get();
        createFileForOfflineParser(url,paserFileDneprRun);

        Elements resultRows = doc.select("table").get(1).select("tr").select("li");

        for (int i = 0; i < resultRows.size(); i++) {
            if (resultRows.get(i).text().indexOf(":") == -1) continue;

            String[] word = resultRows.get(i).text().trim().split("\\s+");


            Result result = new Result();
            result.setTime(setTime(word[word.length - 1]));

            List<Result> resultList = new LinkedList<Result>();
            resultList.add(result);

            Runner runner = new Runner();
            runner.setSurname(word[0]);
            runner.setName(word[1]);
            runner.setSex(setSex(word[word.length - 2].charAt(0)));
            runner.setResults(resultList);
            runners.add(runner);
        }
        return runners;
    }

    public Sex setSex (Character sex){
        if (sex.equals('Ж') || sex.equals('ж')){
         return Sex.FEMALE; }
        else
       return Sex.MALE;
   }

    public Time setTime (String st){
        Time time = new Time();
        StringTokenizer token = new StringTokenizer (st);
        StringTokenizer timeToken = new StringTokenizer(token.nextToken(),":,; ");
        if(timeToken.countTokens()<3)
            time.setHours(0);
        else
        time.setHours(Integer.valueOf(timeToken.nextToken()));
        time.setMinutes(Integer.valueOf(timeToken.nextToken()));
        time.setSeconds(Integer.valueOf(timeToken.nextToken()));
        return time;
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

    public String parseToJson(String url){
        List<Runner> runnerList = null;
        try {
            runnerList = parse(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(runnerList);
    }
}
