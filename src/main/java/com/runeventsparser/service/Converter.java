package com.runeventsparser.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.runeventsparser.bom.Result;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Николай on 01.02.2016.
 */
public class Converter {

    private String jsonFile = "parserData.json";


    public  void convertToJson() throws IOException {
        TopRunnersParser topRunnersParser = new TopRunnersParser();
        String path = "http://toprunners.org/results/35-pobeg-iz-mzhigorya.html";
        List<Result> resultList = topRunnersParser.parse(path);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(jsonFile),resultList );
    }
    public List<Result> convertToJava() throws IOException {
      Gson gson = new Gson();
        String json;
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( new FileInputStream( jsonFile), "utf-8" ) );
        json = bufferedReader.readLine();
        Type type = new TypeToken<List<Result>>(){}.getType();
        List<Result> listFromJson= gson.fromJson(json,type);
        return listFromJson;
    }

}
