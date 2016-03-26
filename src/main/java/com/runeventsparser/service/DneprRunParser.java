package com.runeventsparser.service;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.google.gson.Gson;
import com.runeventsparser.bom.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;



public class DneprRunParser {
    public List<Result> parseSameOnManege () throws IOException {
        String url ="http://dneprrun.dp.ua/sorevnovanija/te-zhe-na-manezhe-2016";
        String pathOffline = "src/main/java/com/runeventsparser/Files/HtmlFiles/te-zhe-na-manezhe-2016.html";
        Document doc = null;
        List<Result> resultList = new LinkedList<Result>();
        CreateFile cf = new CreateFile();

        if((new File(pathOffline).exists())) {
            doc = Jsoup.parse(new File(pathOffline),"utf-8");
        }
        else {
            try {
                doc = Jsoup.connect(url).get();
                cf.createFileForOfflineParser(url, pathOffline);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public List<Result> parseSpringOnNose (String pathXls, String url) throws IOException {
        String pathOffline = "src/main/java/com/runeventsparser/Files/HtmlFiles/vesna-na-nosu-2016.html";
        List<Result> resultList = new LinkedList<Result>();
        Document docUrl = null;
        HSSFRow row;
        HSSFWorkbook excelBook = new HSSFWorkbook(new FileInputStream(pathXls));

        for (int i = 1; i <= excelBook.getSheetAt(0).getPhysicalNumberOfRows(); i++) {
            row = excelBook.getSheetAt(0).getRow(i);
            if (row.getCell(1) != null) {
                Result result = new Result();

                result.setTime(setTime(row.getCell(3).getStringCellValue()));

                Distance distance = new Distance();
                distance.setLength(Double.valueOf(row.getCell(1).getStringCellValue().substring(0, row.getCell(1).getStringCellValue().length() - 1)) / 1000);
                distance.setName(row.getCell(1).getStringCellValue());
                result.setDistance(distance);

                Runner runner = new Runner();
                runner.setSurname(row.getCell(2).getStringCellValue().substring(0, row.getCell(2).getStringCellValue().lastIndexOf(" ")));
                runner.setName(row.getCell(2).getStringCellValue().substring(row.getCell(2).getStringCellValue().substring(0, row.getCell(2).getStringCellValue().lastIndexOf(" ")).length()).trim());
                result.setRunner(runner);

                if (row.getCell(5) == null) {
                    runner.setSex(Sex.FEMALE);
                } else runner.setSex(Sex.MALE);
                resultList.add(result);
            }else{
                excelBook.close();
                break;
            }
        }

        CreateFile cf = new CreateFile();
        if ((new File(pathOffline).exists())) {
            docUrl = Jsoup.parse(new File(pathOffline), "utf-8");
        } else {
            try {
                docUrl = Jsoup.connect(url).get();
                cf.createFileForOfflineParser(url, pathOffline);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Elements resultRows = docUrl.select("table").get(1).select("tr").select("li");
        for (int i = 0; i < resultRows.size(); i++) {
            String[] wordNumb = resultRows.get(i).text().trim().split("\\s+");
            for (int j = 0; j < resultList.size(); j++) {
                if (wordNumb[1].equals(resultList.get(j).getRunner().getSurname()) && wordNumb[2].equals(resultList.get(j).getRunner().getName())) {
                    resultList.get(j).setNumber(wordNumb[0]);
                }
            }
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

    public String parseToJson(List<Result> resultList) throws IOException {
        return new Gson().toJson(resultList);
    }
}
