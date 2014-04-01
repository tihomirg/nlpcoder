package examples.java.util.linkedhashmap;
import java.util.LinkedHashMap;

public class T5 {
  public static void main(String[] args) {
    LinkedHashMap<String,String> lHashMap = new LinkedHashMap<String,String>();

    lHashMap.put("1", "One");
    lHashMap.put("2", "Two");
    lHashMap.put("3", "Three");

    boolean blnExists = lHashMap.containsValue("Two");
    System.out.println(blnExists);
  }
}