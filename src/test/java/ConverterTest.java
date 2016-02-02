import com.runeventsparser.bom.Result;
import com.runeventsparser.service.Converter;
import com.runeventsparser.service.TopRunnersParser;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Николай on 01.02.2016.
 */
public class ConverterTest {
    @Test
    public void converterTest(){

        String path = "http://toprunners.org/results/35-pobeg-iz-mzhigorya.html";

        TopRunnersParser topRunnersParser = new TopRunnersParser();

        List<Result> resultList = null;
        try {
            resultList = topRunnersParser.parse(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonFile = "parserDatas.json";

        Converter converter = new Converter();

        List<Result> listFromJson = new ArrayList<Result>();
        try {
            converter.convertToJson();
            listFromJson = converter.convertToJava();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(new File(jsonFile));
        assertEquals(resultList.size(), listFromJson.size());
        assertEquals(resultList.get(0).getRunner().getName(),listFromJson.get(0).getRunner().getName());
        assertEquals(resultList.get(0).getRunner().getSurname(),listFromJson.get(0).getRunner().getSurname());
        assertEquals(resultList.get(0).getDistance(),listFromJson.get(0).getDistance());
        assertEquals(resultList.get(0).getNumber(),listFromJson.get(0).getNumber());
        assertEquals(resultList.get(0).getRace(),listFromJson.get(0).getRace());
        assertEquals(resultList.get(0).getTime(),listFromJson.get(0).getTime());

    }
}
