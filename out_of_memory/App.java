
import java.util.List;
import java.util.ArrayList;

class LeakObject {
    char[] data = new char[10000000];
}

public class App {

    public static void main( String[] args ) {
        List<LeakObject> dataList = new ArrayList<LeakObject>();

        while (true) {
            dataList.add(new LeakObject());
        }
    }
}

