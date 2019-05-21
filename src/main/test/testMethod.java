import com.service.base;

import java.util.List;

public class testMethod {
    private static void test(){
        base test = new base();
        String string = "10001+10002+10003+56892+34221";
        List<Long> list = test.stringToLongList(string);
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
}
