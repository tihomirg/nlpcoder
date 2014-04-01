package examples.java.util.collections;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class T1 {
  public static void main(String args[]) {
    List list = Collections.EMPTY_LIST;
    Set set = Collections.EMPTY_SET;
    Map map = Collections.EMPTY_MAP;

    List<String> s = Collections.emptyList();
    Set<Long> l = Collections.emptySet();
    Map<Date, String> d = Collections.emptyMap();
  }
}