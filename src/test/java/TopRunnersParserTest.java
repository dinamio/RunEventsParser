import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.runeventsparser.bom.Result;
import com.runeventsparser.service.Converter;
import com.runeventsparser.service.TopRunnersParser;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Николай on 31.01.2016.
 */

public class TopRunnersParserTest {
    @Test
    public  void parserTest() {
        String parserFile ="dataForParser.html";
        String path = "http://toprunners.org/results/35-pobeg-iz-mzhigorya.html";
        String json;
        TopRunnersParser topRunnersParser = new TopRunnersParser();
        Gson gson = new Gson();
        json = topRunnersParser.parseToJson(path);
        List<Result> resultList = null;
        try {
            resultList = topRunnersParser.parse(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type type = new TypeToken<List<Result>>(){}.getType();
        List<Result> listFromJson= gson.fromJson(json,type);
        assertNotNull(parserFile);
        assertEquals(resultList.size(), listFromJson.size());
        assertEquals(resultList.get(0).getRunner().getName(),listFromJson.get(0).getRunner().getName());
        assertEquals(resultList.get(0).getRunner().getSurname(),listFromJson.get(0).getRunner().getSurname());
        assertEquals(resultList.get(0).getDistance(),listFromJson.get(0).getDistance());
        assertEquals(resultList.get(0).getNumber(),listFromJson.get(0).getNumber());
        assertEquals(resultList.get(0).getRace(),listFromJson.get(0).getRace());
        assertEquals(resultList.get(0).getTime(),listFromJson.get(0).getTime());
    }
}

