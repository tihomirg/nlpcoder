package examples.java.util.map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class T9 {

  public static void main(String[] argv) {
    Map map = new HashMap();

    map.put("Adobe", "Mountain View, CA");
    map.put("IBM", "White Plains, NY");
    map.put("Learning Tree", "Los Angeles, CA");

    Iterator k = map.keySet().iterator();
    while (k.hasNext()) {
      String key = (String) k.next();
      System.out.println("Key " + key + "; Value " + (String) map.get(key));
    }
  }
}