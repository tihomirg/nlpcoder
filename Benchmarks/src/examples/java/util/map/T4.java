package examples.java.util.map;
import java.util.HashMap;
import java.util.Map;

public class T4 {
  public static void main(String[] a) {
    Map<String,String> map = new HashMap<String,String>();
    map.put("key1", "value1");
    map.put("key2", "value2");
    map.put("key3", "value3");

    System.out.println(map.containsKey("key1"));
    System.out.println(map.containsValue("value2"));
  }
}