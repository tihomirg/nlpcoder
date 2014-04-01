package examples.java.util.map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class T1 {
  public static void main(String[] a) {
    Map<String,String> map = new HashMap<String,String>();
    map.put("key1", "value1");
    map.put("key2", "value2");
    map.put("key3", "value3");
    Set set = map.entrySet();

    Iterator iter = set.iterator();

    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      System.out.println(entry.getKey() + " -- " + entry.getValue());
    }
  }
}