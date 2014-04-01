package examples.java.util.map;
import java.util.HashMap;
import java.util.Map;

public class T5 {
  public static void main(String[] a) {
    Map<String,String> map = new HashMap<String,String>();
    map.put("key1", "value1");
    map.put("key2", "value2");
    map.put("key3", "value3");
    Map<String,String> map2 = new HashMap<String,String>();
    map2.put("key2", "value2");
    map2.put("key1", "value1");
    map2.put("key3", "value3");
    System.out.println(map2.equals(map2));
  }
}