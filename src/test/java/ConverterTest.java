import com.runeventsparser.bom.Result;
import com.runeventsparser.service.Converter;
import com.runeventsparser.service.DneprRunParser;
import com.runeventsparser.service.VseProbegiParser;
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
//        String pathForWomenRace="http://vseprobegi.org/img/snowrun16_women_results.htm";  // podlesny
//        String pathForMenRace = "http://vseprobegi.org/img/snowrun16_men_results.htm";    // podlesny
//        String jsonFile = "src/main/java/com/runeventsparser/Files/JsonFiles/Vse.json";   // podlesny
//        List<String> pathList = new ArrayList<String>();          // podlesny
//        pathList.add(pathForMenRace);                             // podlesny
//        pathList.add(pathForWomenRace);                           // podlesny
//        VseProbegiParser vseProbegiParser = new VseProbegiParser();  // podlesny

        Converter converter = new Converter();
        List<Result> listFromJson = new ArrayList<Result>();
        List<Result> resultList = null;
        DneprRunParser dneprRunParser = new DneprRunParser();  //  tolchelnikov
        String urlSomeOnManege ="http://dneprrun.dp.ua/sorevnovanija/te-zhe-na-manezhe-2016";  //  tolchelnikov
        String jsonFile = "src/main/java/com/runeventsparser/Files/JsonFiles/TheSameOnTheManege.json"; //  tolchelnikov
        String json = null;

        try {
//              json = vseProbegiParser.parseToJson(pathList); // podlesny
//              resultList = vseProbegiParser.parse(pathList);   // podlesny
            json= dneprRunParser.parseToJson(urlSomeOnManege,jsonFile); //  tolchelnikov
            resultList = dneprRunParser.parseSameOnManege(urlSomeOnManege,jsonFile);  //  tolchelnikov


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
