package com.runeventsparser.service;


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

    public  void convertToJson(String jsonFile) throws IOException {
        String path ="http://toprunners.org/results/35-pobeg-iz-mzhigorya.html";
        String json;
        TopRunnersParser topRunnersParser = new TopRunnersParser();
        json=topRunnersParser.parseToJson(path);
        BufferedWriter out = new BufferedWriter(new PrintWriter(jsonFile,"utf-8"));
        out.write(json);
        out.close();

    }
    public List<Result> convertToJava(String jsonFile) throws IOException {
      Gson gson = new Gson();
        String json;
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( new FileInputStream( jsonFile), "utf-8" ) );
        json = bufferedReader.readLine();
        Type type = new TypeToken<List<Result>>(){}.getType();
        List<Result> listFromJson= gson.fromJson(json,type);
        return listFromJson;
    }

}
