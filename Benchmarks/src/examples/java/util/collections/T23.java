package examples.java.util.collections;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

public class T23 {
  public static void main(String[] s) {
    Hashtable<String,String> table = new Hashtable<String,String>();
    table.put("key1", "value1");
    table.put("key2", "value2");
    table.put("key3", "value3");

    Map m = Collections.unmodifiableMap(table);

    m.put("key3", "value3");

    System.out.println(m);

  }
}