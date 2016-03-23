import com.runeventsparser.bom.Result;
import com.runeventsparser.service.Converter;
import com.runeventsparser.service.DneprRunParser;
import com.runeventsparser.service.TopRunnersParser;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Николай on 23.03.2016.
 */
public class ConvertTopRunners {
    @Test
    public void converterTest(){
        String path = "http://toprunners.org/results/155-7-oy-odesskiy-mezhdunarodnyy-marafon-samoprevoshozhdenie.html";
        String jsonFile = "src/main/java/com/runeventsparser/Files/JsonFiles/TopRunners.json";
        List<String> pathList = new ArrayList<String>();
        pathList.add(path);
        TopRunnersParser topRunnersParser = new TopRunnersParser();

        Converter converter = new Converter();
        List<Result> listFromJson = new ArrayList<Result>();
        List<Result> resultList = null;
        String json = null;


        try {
              json = topRunnersParser.parseToJson(pathList);
              resultList = topRunnersParser.parse(pathList);
            converter.convertToJson(jsonFile,json);
            listFromJson = converter.convertToJava(jsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(new File(jsonFile));
        assertEquals(resultList.size(), listFromJson.size());
        assertEquals(resultList.get(0).getRunner().getName(),listFromJson.get(0).getRunner().getName());
        assertEquals(resultList.get(0).getRunner().getSurname(),listFromJson.get(0).getRunner().getSurname());
        assertEquals(resultList.get(0).getDistance().getName(),listFromJson.get(0).getDistance().getName());
        assertEquals(resultList.get(0).getDistance().getLength(),listFromJson.get(0).getDistance().getLength());
        assertEquals(resultList.get(0).getNumber(),listFromJson.get(0).getNumber());
        assertEquals(resultList.get(0).getRace(),listFromJson.get(0).getRace());
        assertEquals(resultList.get(0).getTime(),listFromJson.get(0).getTime());
        assertEquals(resultList.get(0).getRunner().getSex(),listFromJson.get(0).getRunner().getSex());
    }
}
