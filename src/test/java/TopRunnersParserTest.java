import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.runeventsparser.bom.Result;
import com.runeventsparser.bom.Sex;
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

        String path = "http://vseprobegi.org/img/snowrun16_men_results.htm";

        TopRunnersParser topRunnersParser = new TopRunnersParser();

        List<Result> resultList = null;

        try {
            resultList = topRunnersParser.parse(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(parserFile);
        assertEquals(191,resultList.size());
        assertEquals("liebiediev",resultList.get(14).getRunner().getSurname());
        assertEquals("yehor",resultList.get(14).getRunner().getName());
        assertEquals(Sex.MALE, resultList.get(14).getRunner().getSex());
        assertEquals("236",resultList.get(14).getNumber());
        assertEquals("00:36:57",resultList.get(14).getTime().getTimeToString());
        assertEquals("10 km",resultList.get(14).getDistance().getName());
        assertEquals(String.valueOf(10.0), String.valueOf(resultList.get(14).getDistance().getLength()));

    }
}

