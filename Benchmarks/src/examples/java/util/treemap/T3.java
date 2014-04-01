package examples.java.util.treemap;
import java.util.TreeMap;

public class T3 {
  public static void main(String[] a) {
    TreeMap<String,String> map = new TreeMap<String,String>();
    map.put("key1", "value1");
    map.put("key2", "value2");
    map.put("key3", "value3");

    if (!map.isEmpty()) {
      String last = map.lastKey();
      boolean first = true;
      do {
        if (!first) {
          System.out.print(", ");
        }
        System.out.print(last);
        last = map.headMap(last, true).lastKey();
        first = false;
      } while (last != map.firstKey());
      System.out.println();
    }
  }
}