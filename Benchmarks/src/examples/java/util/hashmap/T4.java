package examples.java.util.hashmap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class T4 {
  public static void main(String args[]) {

    HashMap<String, Double> hm = new HashMap<String, Double>();

    hm.put("A", new Double(3434.34));
    hm.put("B", new Double(123.22));
    hm.put("C", new Double(1378.00));
    hm.put("D", new Double(99.22));
    hm.put("E", new Double(-19.08));

    Set<Map.Entry<String, Double>> set = hm.entrySet();

    for (Map.Entry<String, Double> me : set) {
      System.out.print(me.getKey() + ": ");
      System.out.println(me.getValue());
    }

    System.out.println();

    double balance = hm.get("B");
    hm.put("B", balance + 1000);

    System.out.println("B's new balance: " + hm.get("B"));
  }
}