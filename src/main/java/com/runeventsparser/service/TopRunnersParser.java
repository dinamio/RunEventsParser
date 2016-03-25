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
 * Created by ??????? on 31.01.2016.
 */
public class TopRunnersParser {

    String parserFile = "src/main/java/com/runeventsparser/Files/HtmlFiles/TopRunners.html";


    public List<Result> parse(List<String> path) throws IOException {
        List<Result> resultList = parseTopRunners(path.get(0));
        return resultList;
    }

    public Result parseResultRow(String runnerData, Distance distance){

        Runner runner = new Runner();

        Result result = new Result();

        StringTokenizer token = new StringTokenizer (runnerData);


        Time time = new Time();

        String tableNumber = token.nextToken();

        String garbage = token.nextToken();
        result.setNumber(token.nextToken());
        result.setDistance(distance);


        if(token.countTokens()>8){
            String surname="";
            surname = token.nextToken();
            surname+=token.nextToken();
            runner.setSurname(surname);
            runner.setName(token.nextToken());
        }
        else{

            runner.setSurname(token.nextToken());
            runner.setName(token.nextToken());
        }
        String sex=token.nextToken();

        if(sex.contains("М"))
            runner.setSex(Sex.MALE);
        else
        runner.setSex(Sex.FEMALE);
        garbage = token.nextToken();



        StringTokenizer timeToken = new StringTokenizer(token.nextToken(),":,; ");
        if(timeToken.countTokens()<3)
            time.setHours(0);
        else
            time.setHours(Integer.valueOf(timeToken.nextToken()));

        time.setMinutes(Integer.valueOf(timeToken.nextToken()));
        time.setSeconds(Integer.valueOf(timeToken.nextToken()));
        result.setTime(time);
        result.setRunner(runner);
        if(distance.getLength()==42.0) {
            if (tableNumber.equals("34") || tableNumber.equals("40"))
                result = switchNameAndSurname(result);
        }
        else
        {
            List<String> runnerWhoNeedSwitch = new ArrayList<String>();
            runnerWhoNeedSwitch.add("303");
            runnerWhoNeedSwitch.add("247");
            runnerWhoNeedSwitch.add("234");
            runnerWhoNeedSwitch.add("195");
            runnerWhoNeedSwitch.add("116");
            runnerWhoNeedSwitch.add("82");
           if(runnerWhoNeedSwitch.contains(tableNumber))
            result = switchNameAndSurname(result);
        }
        return result;

    }

    public List<Result> parseTopRunners(String path) throws IOException {
        List<Result> totalResultList = new ArrayList<Result>();
        CreateFile createFile = new CreateFile();
        Document doc=null;
        if((new File(parserFile).exists())) {
            doc = Jsoup.parse(new File(parserFile),"utf-8");
        }
        else {
            try {
                doc = Jsoup.connect(path).get();
                createFile.createFileForOfflineParser(path, parserFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(int j=0;j<2;j++) {
            List<Result> currentResultList = new ArrayList<Result>();
            Element table = doc.select("table").get(j);
            Elements rows = table.select("tr");
            Distance distance = new Distance();
            if(j==0) {
                distance.setName("42 км");
                distance.setLength(42.0);
            }
            else{
                distance.setName("21 км");
                distance.setLength(21.0);
            }
            for (int i = 1; i < rows.size(); i++) {
                String resultData = rows.get(i).text();

                try {
                    Result result = parseResultRow(resultData, distance);
                    currentResultList.add(result);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            totalResultList.addAll(currentResultList);
        }
        return totalResultList;
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
    public Result switchNameAndSurname(Result result){
        String surname = result.getRunner().getSurname();
        Runner runner = result.getRunner();
        runner.setSurname(result.getRunner().getName());
        runner.setName(surname);
        result.setRunner(runner);
        return result;
    }
}
