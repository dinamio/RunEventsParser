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

    String parserFileForMen = "src/main/java/com/runeventsparser/Files/HtmlFiles/VseProbegiMen.html";
    String parserFileForWomen = "src/main/java/com/runeventsparser/Files/HtmlFiles/VseProbegiWomen.html";

    public List<Result> parse(List<String> path) throws IOException {
        List<Result> resultList = parseRaceForMen(path.get(0));
        resultList.addAll(parseRaceForWomen(path.get(1)));
        return resultList;
    }

    public Result parseResultRow(String runnerData, Distance distance, Sex sex){

        Runner runner = new Runner();

        Result result = new Result();

        StringTokenizer token = new StringTokenizer (runnerData);

        Time time = new Time();

        String garbage = token.nextToken();

        runner.setSex(sex);
        result.setDistance(distance);
        if(token.countTokens() > 7) {
            result.setNumber(token.nextToken());
            runner.setSurname(token.nextToken());
            String name=token.nextToken()+" ";
            name+=token.nextToken();
            runner.setName(name);
        }
        else{
            result.setNumber(token.nextToken());
            runner.setSurname(token.nextToken());
            runner.setName(token.nextToken());
        }

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

    public List<Result> parseRaceForMen(String path) throws IOException {
        List<Result> resultList = new ArrayList<Result>();
        Document doc=null;
        if((new File(parserFileForMen).exists())) {
            doc = Jsoup.parse(new File(parserFileForMen),"utf-8");
        }
        else {
            try {
                doc = Jsoup.connect(path).get();
                createFileForOfflineParser(path,parserFileForMen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Element table = doc.select("table").get(0);
        Elements rows = table.select("tr");
        Distance distance = new Distance();
        distance.setName("10 км");
        distance.setLength(10.0);
        for (int i = 4; i <=194; i++) {
            String resultData = rows.get(i).text();
            try {
                Result result = parseResultRow(resultData,distance,Sex.MALE);
                resultList.add(result);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }
        }
        return resultList;
    }

    public List<Result> parseRaceForWomen(String path) throws IOException {
        List<Result> resultList = new ArrayList<Result>();
        Document doc=null;
        if((new File(parserFileForWomen).exists())) {
            doc = Jsoup.parse(new File(parserFileForWomen),"utf-8");
        }
        else {
            try {
                doc = Jsoup.connect(path).get();
                createFileForOfflineParser(path,parserFileForWomen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Element table = doc.select("table").get(0);
        Elements rows = table.select("tr");
        Distance distance = new Distance();
        distance.setName("5 км");
        distance.setLength(5.0);
        for (int i = 4; i <=94; i++) {
            String resultData = rows.get(i).text();
            try {
                Result result = parseResultRow(resultData,distance,Sex.FEMALE);
                resultList.add(result);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }
        }
        return resultList;

    }

    public String parseToJson(List<String> path){
        List<Result> resultList = null;
        try {
            resultList = parse(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(resultList);
    }


    public void createFileForOfflineParser(String path, String parserFile){
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
