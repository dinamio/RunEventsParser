package com.runeventsparser.service;



import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.google.gson.Gson;
import com.runeventsparser.bom.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



public class DneprRunParser {

    public List<Result> parseSameOnManege (String url, String path) throws IOException {

        Document doc;
        List<Result> resultList = new LinkedList<Result>();

        doc = Jsoup.connect(url).get();
        CreateFile cf = new CreateFile();
        cf.createFileForOfflineParser(url,path);
        Elements resultRows = doc.select("table").get(1).select("tr").select("li");

        for (int i = 8; i < resultRows.size(); i++) {
            if (resultRows.get(i).text().indexOf(":") == -1) {
                if (Character.isDigit(resultRows.get(i).text().charAt(0))){
                    String[] wordNumb = resultRows.get(i).text().trim().split("\\s+");
                    for(int j = 0; j<resultList.size(); j++ ){
                        if (wordNumb[1].equals(resultList.get(j).getRunner().getSurname()) && wordNumb[2].equals(resultList.get(j).getRunner().getName())){
                            resultList.get(j).setNumber(wordNumb[0]);
                        }
                    }
                }
                continue;
            }

            String[] word = resultRows.get(i).text().trim().split("\\s+");

            Result result = new Result();
            result.setTime(setTime(word[word.length - 1]));
            resultList.add(result);

            Distance distance = new Distance();
            if (i <= 18){
            distance.setName("1609 метров");
            distance.setLength(1.609);
            }else {
                distance.setName("3000 метров");
                distance.setLength(3.0);
            }

            Runner runner = new Runner();
            runner.setSurname(word[0]);
            runner.setName(word[1]);
            runner.setSex(Sex.convertSex(word[word.length - 2].charAt(0)));

            result.setDistance(distance);
            result.setRunner(runner);
        }
        return resultList;
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

    public String parseToJson(String url,String path) throws IOException {
        return new Gson().toJson(parseSameOnManege(url,path));
    }
}
