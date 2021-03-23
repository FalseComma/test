import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test1 {

    @Test
    public void Test(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()));
    }
}
