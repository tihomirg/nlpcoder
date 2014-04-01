package examples.java.util.set;
import java.util.HashSet;
import java.util.Set;

public class T1 {

  public static void main(String[] a) {
    Set<String> set = new HashSet<String>();
    set.add("Hello");
    if (set.add("Hello")) {
      System.out.println("addition successful");
    } else {
      System.out.println("addition failed");
    }
  }
}