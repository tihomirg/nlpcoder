package examples.java.util.linkedhashmap;
import java.util.LinkedHashMap;

public class T9 {
  public static void main(String[] args) {
    LinkedHashMap<String,String> lHashMap = new LinkedHashMap<String,String>();

    System.out.println(lHashMap.size());

    lHashMap.put("1", "One");
    lHashMap.put("2", "Two");
    lHashMap.put("3", "Three");

    System.out.println(lHashMap.size());

    Object obj = lHashMap.remove("2");
    System.out.println(lHashMap.size());

  }

}