import com.runeventsparser.bom.Result;
import com.runeventsparser.bom.Sex;
import com.runeventsparser.service.VseProbegiParser;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ������� on 31.01.2016.
 */

public class VseProbegiParserTest {
    @Test
    public  void parserTest() {

        VseProbegiParser vseProbegiParser = new VseProbegiParser();

        List<Result> resultList = null;

        String pathForMenRace ="http://vseprobegi.org/img/snowrun16_men_results.htm";
        String pathForWomenRace="http://vseprobegi.org/img/snowrun16_women_results.htm";
        List<String> pathList = new ArrayList<String>();
        pathList.add(pathForMenRace);
        pathList.add(pathForWomenRace);

        try {
            resultList = vseProbegiParser.parse(pathList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(282,resultList.size());
        assertEquals("liebiediev",resultList.get(14).getRunner().getSurname());
        assertEquals("yehor",resultList.get(14).getRunner().getName());
        assertEquals(Sex.MALE, resultList.get(14).getRunner().getSex());
        assertEquals("236",resultList.get(14).getNumber());
        assertEquals("00:36:57",resultList.get(14).getTime().getTimeToString());
        //TODO Assert distance.getName();
        assertEquals(String.valueOf(10.0), String.valueOf(resultList.get(14).getDistance().getLength()));

    }
}

