package examples.java.util.hashtable;
import java.util.Dictionary;
import java.util.Hashtable;

public class T14 {
  public static void main(String args[]) {
    Hashtable ht = new Hashtable();
    ht.put("a", "a");
    ht.put("b", new Double(2));
    ht.put("c", "b");
    ht.put("d", new Integer(30));
    show(ht);
  }

  static void show(Dictionary d) {
    System.out.println("a: " + d.get("a"));
    System.out.println("b: " + d.get("b"));
    System.out.println("c: " + d.get("c"));
    System.out.println("d: " + d.get("d"));
  }
}