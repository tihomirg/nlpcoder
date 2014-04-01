package examples.java.util.hashtable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class T11 {
  public static void main(String args[]) {
    Hashtable<String, Double> balance = new Hashtable<String, Double>();

    String str;
    double bal;

    balance.put("A", 4.34);
    balance.put("B", 3.22);
    balance.put("C", 8.00);
    balance.put("D", 9.22);
    balance.put("E", -9.08);

    Set<String> set = balance.keySet();

    Iterator<String> itr = set.iterator();
    while (itr.hasNext()) {
      str = itr.next();
      System.out.println(str + ": " + balance.get(str));
    }

    System.out.println();

    bal = balance.get("A");
    balance.put("A", bal + 1000);
    System.out.println("A's new balance: " + balance.get("A"));
  }
}