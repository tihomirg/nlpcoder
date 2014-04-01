package examples.java.util.linkedhashmap;
import java.util.LinkedHashMap;

public class T1 {

  public static void main(String[] args) {
    LinkedHashMap<Integer, Integer> linkedMap = new LinkedHashMap<Integer, Integer>();
    for (int i = 0; i < 10; i++) {
      linkedMap.put(i, i);
    }

    System.out.println(linkedMap);
    // Least-recently used order:
    linkedMap = new LinkedHashMap<Integer, Integer>(16, 0.75f, true);

    for (int i = 0; i < 10; i++) {
      linkedMap.put(i, i);
    }
    System.out.println(linkedMap);
    for (int i = 0; i < 7; i++)
      System.out.println(linkedMap.get(i));
    System.out.println(linkedMap);
  }
}