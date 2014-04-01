package examples.java.util.linkedhashmap;
import java.util.LinkedHashMap;

public class T6 {
  public static void main(String[] args) {
    LinkedHashMap<String,Integer> lHashMap = new LinkedHashMap<String,Integer>();

    lHashMap.put("One", new Integer(1));
    lHashMap.put("Two", new Integer(2));


    Object obj = lHashMap.get("One");
    System.out.println(obj);
  }
}