package com.runeventsparser.service;

import com.google.gson.Gson;
import com.runeventsparser.bom.*;
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

    String parserFile = "src/main/java/com/runeventsparser/Files/HtmlFiles/VseProbegi.html";

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

        Element table = doc.select("table").get(0);
        Elements rows = table.select("tr");
        for (int i = 4; i <=194; i++) {
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

        Distance distance = new Distance();

        Runner runner = new Runner();

        Result result = new Result();

        StringTokenizer token = new StringTokenizer (runnerData);

        String garbage="";

        Time time = new Time();

        String flag = token.nextToken();

        distance.setLength(10.0);
        distance.setName("10 km");
        runner.setSex(Sex.MALE);
        result.setDistance(distance);
        result.setNumber(token.nextToken());
        runner.setSurname(token.nextToken());
        runner.setName(token.nextToken());

        garbage=token.nextToken();
        garbage=token.nextToken();
        garbage=token.nextToken();

        StringTokenizer timeToken = new StringTokenizer(token.nextToken(),":,; ");
        if(timeToken.countTokens()<3)
            time.setHours(0);
        else
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
