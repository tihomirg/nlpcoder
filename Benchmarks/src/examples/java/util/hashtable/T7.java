package examples.java.util.hashtable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class T7 {
  public static void main(String args[]) throws Exception {
    Hashtable hash = new Hashtable(89);
    hash.put("one", "two");
    hash.put("two", "three");
    hash.put("three", "four");
    hash.put("four", "five");
    System.out.println(hash);
    System.out.println(hash.size());
    Enumeration e = hash.keys();
    while (e.hasMoreElements()) {
      String key = (String) e.nextElement();
      System.out.println(key + " : " + hash.get(key));
    }
    Set set = hash.entrySet();
    Iterator it = set.iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      System.out.println(entry.getKey() + " : " + entry.getValue());
    }
  }
}