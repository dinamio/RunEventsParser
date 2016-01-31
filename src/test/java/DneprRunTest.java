import com.runeventsparser.service.DneprRunParser;
import org.junit.Test;

/**
 * Created by Николай on 30.01.2016.
 */
public class DneprRunTest {
    @Test
   public void dneprRunTest(){
        DneprRunParser dneprRunParser = new DneprRunParser();
        dneprRunParser.connect();
    }
}
