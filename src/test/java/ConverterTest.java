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
 * Created by ������� on 01.02.2016.
 */
public class ConverterTest {
    @Test
    public void converterTest(){

        String path = "http://vseprobegi.org/img/snowrun16_men_results.htm";

        TopRunnersParser topRunnersParser = new TopRunnersParser();

        List<Result> resultList = null;

        String jsonFile = "src/main/java/com/runeventsparser/Files/JsonFiles/TopRunnersTables.json";

        try {
            resultList = topRunnersParser.parse(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Converter converter = new Converter();

        List<Result> listFromJson = new ArrayList<Result>();
        try {
            converter.convertToJson(jsonFile);
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
