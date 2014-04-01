package examples.java.util.hashmap;
import java.util.HashMap;

public class T6 {
  public static void main(String[] args) {
    HashMap<String, String> hMap = new HashMap<String, String>();

    hMap.put("1", "One");
    hMap.put("2", "Two");
    hMap.put("3", "Three");

    boolean blnExists = hMap.containsKey("3");
    System.out.println("3 exists in HashMap ? : " + blnExists);
  }
}