package examples.java.util.map;
import java.util.HashMap;
import java.util.Map;

public class T11 {
  public static void main(String[] a) {
    Map<String,String> map = new HashMap<String,String>();
    map.put("key1", "value1");
    map.put("key2", "value2");
    map.put("key3", "value3");
    map.put(null, null);

    Map<String,String> map2 = new HashMap<String,String>();
    map2.put("key4", "value4");
    map2.put("key5", "value5");
    map2.put("key6", "value6");
    map.putAll(map2);

    System.out.println(map);
  }
}