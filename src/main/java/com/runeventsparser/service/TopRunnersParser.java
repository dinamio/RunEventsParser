package com.runeventsparser.service;

import com.google.gson.Gson;
import com.runeventsparser.bom.Result;
import com.runeventsparser.bom.Runner;
import com.runeventsparser.bom.Time;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by ������� on 31.01.2016.
 */
public class TopRunnersParser {

    private static final String DQ = "DQ";

    String parserFile = "src/main/java/com/runeventsparser/Files/HtmlFiles/TopRunners.html";

    public List<Result> parse(String path) throws IOException {

        Document doc  = null;
        List<Result> resultList = new ArrayList<Result>();
        if((new File(parserFile).exists())) {
                doc = Jsoup.parse(new File(parserFile),"utf-8");
        }
        else {
            try {
                doc = Jsoup.connect(path).get();
                createFileForOfflineParser(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Element table = doc.select("table").get(1);
        Elements rows = table.select("tr");//TODO Fix if row has not name and surname
        for (int i = 0; i < rows.size(); i++) {
            String resultData = rows.get(i).text();
            try {
                Result result = parseResultRow(resultData);
                resultList.add(result);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }
        }
        return resultList;
    }
    public Result parseResultRow(String runnerData){
        Runner runner = new Runner();

        Result result = new Result();

        StringTokenizer token = new StringTokenizer (runnerData);

        Time time = new Time();
        String flag = token.nextToken();
        if(flag.equals(DQ))throw new IllegalArgumentException("Runner is DQ");
        runner.setName(token.nextToken());
        runner.setSurname(token.nextToken());
        result.setNumber(token.nextToken());
        StringTokenizer timeToken = new StringTokenizer(token.nextToken(),":,; ");
        time.setHours(Integer.valueOf(timeToken.nextToken()));
        time.setMinutes(Integer.valueOf(timeToken.nextToken()));
        time.setSeconds(Integer.valueOf(timeToken.nextToken()));
        result.setTime(time);
        result.setRunner(runner);
        return result;

    }

    public String parseToJson(String path){
        List<Result> resultList = null;
        try {
            resultList = parse(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(resultList);
    }

    public void createFileForOfflineParser(String path){
        try {
            Document doc = Jsoup.connect(path).get();
            BufferedWriter out = new BufferedWriter(new PrintWriter(parserFile,"utf-8"));
            out.write(String.valueOf(doc));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
