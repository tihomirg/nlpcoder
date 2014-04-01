package examples.java.util.hashmap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class T10 {
  public static void main(String[] a) {
    Map<String,String> map = new HashMap<String,String>();
    map.put("key1", "value1");
    map.put("key2", "value2");
    map.put("key3", "value3");

    Collection set = map.values();
    Iterator iter = set.iterator();

    while (iter.hasNext()) {
      System.out.println(iter.next());
    }
  }
}