package examples.java.util.treemap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class T1 {
  public static void main(String args[]) {

    TreeMap<String, Double> tm = new TreeMap<String, Double>();

    tm.put("A", new Double(3434.34));
    tm.put("B", new Double(123.22));
    tm.put("C", new Double(1378.00));
    tm.put("D", new Double(99.22));
    tm.put("E", new Double(-19.08));

    Set<Map.Entry<String, Double>> set = tm.entrySet();

    for (Map.Entry<String, Double> me : set) {
      System.out.print(me.getKey() + ": ");
      System.out.println(me.getValue());
    }
    System.out.println();

    double balance = tm.get("B");
    tm.put("B", balance + 1000);

    System.out.println("B's new balance: " + tm.get("B"));
  }
}
