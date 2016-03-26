import com.runeventsparser.bom.Result;
import com.runeventsparser.service.Converter;
import com.runeventsparser.service.DneprRunParser;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by zOpa on 24.03.2016.
 */
public class ConvertDneprRunTest {
    DneprRunParser dneprRun = new DneprRunParser();
    String pathJsonSameOnManege = "src/main/java/com/runeventsparser/Files/JsonFiles/SameOnManege.json";

    String pathJsonSpringOnNose = "src/main/java/com/runeventsparser/Files/JsonFiles/SpringOnNose.json";
    String pathXlsSpringOnNose = "src/main/java/com/runeventsparser/Files/XlsFiles/Spring.xls";
    String urlSpringOnNose = "http://dneprrun.dp.ua/sorevnovanija/vesna-na-nosu-2016";
    String pathOfflineSpringOnNose = "src/main/java/com/runeventsparser/Files/HtmlFiles/vesna-na-nosu-2016.html";


    String pathJsonRiverFront = "src/main/java/com/runeventsparser/Files/JsonFiles/RiverFront.json";
    String pathXlsRiverFront = "src/main/java/com/runeventsparser/Files/XlsFiles/RiverFront.xls";
    String urlRiverFront = "http://dneprrun.dp.ua/sorevnovanija/riverfront-2016";
    String pathOfflineRiverFront = "src/main/java/com/runeventsparser/Files/HtmlFiles/RiverFront.html";

    @Test
    public void testRun() throws IOException {
        parseDneprRunTest(pathJsonSameOnManege,dneprRun.parseSameOnManege());
        parseDneprRunTest(pathJsonSpringOnNose,dneprRun.parseSpringOnNose(pathXlsSpringOnNose,urlSpringOnNose,pathOfflineSpringOnNose));
        parseDneprRunTest(pathJsonRiverFront,dneprRun.parseSpringOnNose(pathXlsRiverFront,urlRiverFront,pathOfflineRiverFront));
    }
    public void parseDneprRunTest(String jsonFile,List<Result> dneprRun) throws IOException {
        DneprRunParser dneprRunParser = new DneprRunParser();

        Converter converter = new Converter();
        List<Result> listFromJson = new ArrayList<Result>();
        List<Result> resultList = null;
        String json = null;

        json = dneprRunParser.parseToJson(dneprRun);
        resultList = dneprRun;
        converter.convertToJson(jsonFile,json);
        listFromJson = converter.convertToEntity(jsonFile);

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
