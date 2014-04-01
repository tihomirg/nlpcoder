package examples.java.util.hashtable;
import java.util.Enumeration;
import java.util.Hashtable;

public class T18 {
  public static void main(String[] s) {
    Hashtable<String,String> table = new Hashtable<String,String>();
    table.put("key1", "value1");
    table.put("key2", "value2");
    table.put("key3", "value3");

    Enumeration e = table.elements();
    while (e.hasMoreElements()) {
      String key = (String) e.nextElement();
      System.out.println(key + " : " + table.get(key));
    }

    System.out.println(table.values());
  }
}