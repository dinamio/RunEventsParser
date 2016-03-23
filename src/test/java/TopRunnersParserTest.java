import com.runeventsparser.bom.Result;
import com.runeventsparser.bom.Sex;
import com.runeventsparser.service.TopRunnersParser;
import com.runeventsparser.service.VseProbegiParser;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Николай on 23.03.2016.
 */
public class TopRunnersParserTest {
    @Test
    public  void parserTest() {

        TopRunnersParser topRunnersParser = new TopRunnersParser();

        List<Result> resultList = null;

        String path="http://toprunners.org/results/155-7-oy-odesskiy-mezhdunarodnyy-marafon-samoprevoshozhdenie.html";
        List<String> pathList = new ArrayList<String>();
        pathList.add(path);

        try {
            resultList = topRunnersParser.parse(pathList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(111,resultList.size());
        assertEquals("Pisarchyk",resultList.get(31).getRunner().getSurname());
        assertEquals("Valeriy",resultList.get(31).getRunner().getName());
        assertEquals(Sex.MALE, resultList.get(31).getRunner().getSex());
        assertEquals("1069",resultList.get(31).getNumber());
        assertEquals("03:35:54",resultList.get(31).getTime().getTimeToString());
        //TODO Assert distance.getName();
        assertEquals(String.valueOf(42.2), String.valueOf(resultList.get(31).getDistance().getLength()));

    }
}
