package examples.java.util.hashtable;
import java.util.Enumeration;
import java.util.Hashtable;

public class T1 {
  public static void main(String args[]) {
    Hashtable<String, Double> balance = new Hashtable<String, Double>();

    Enumeration<String> names;
    String str;

    balance.put("A", 3434.34);

    names = balance.keys();
    while (names.hasMoreElements()) {
      str = names.nextElement();
      System.out.println(str + ": " + balance.get(str));
    }

    System.out.println();
  }
}