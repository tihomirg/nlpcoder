package examples.java.util.linkedhashmap;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class T10 {
  public static void main(String[] args) {
    LinkedHashMap<String,String> lHashMap = new LinkedHashMap<String,String>();

    lHashMap.put("1", "One");
    lHashMap.put("2", "Two");
    lHashMap.put("3", "Three");

    Collection c = lHashMap.values();
    Iterator itr = c.iterator();

    while (itr.hasNext()){
      System.out.println(itr.next());
    }
  }
}