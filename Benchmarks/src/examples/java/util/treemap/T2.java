package examples.java.util.treemap;
import java.util.TreeMap;

public class T2 {
  public static void main(String args[]) {
    TreeMap map = new TreeMap();
    map.put("Virginia", "Richmond");
    map.put("Massachusetts", "Boston");
    map.put("New York", "Albany");
    map.put("Maryland", "Annapolis");

    if (!map.isEmpty()) {
      Object last = map.lastKey();
      boolean first = true;
      do {
        if (!first) {
          System.out.print(", ");
        }
        System.out.print(last);
        last = map.headMap(last).lastKey();
        first = false;
      } while (last != map.firstKey());
      System.out.println();
    }
  }
}