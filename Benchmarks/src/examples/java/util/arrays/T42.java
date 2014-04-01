package examples.java.util.arrays;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class T42 {
  public static void main(String args[]) throws Exception {
    Comparator comp = Collections.reverseOrder();
    String[] a = new String[] { "a", "b", "c" };
    Arrays.sort(a, comp);
    for (int i = 0, n = a.length; i < n; i++) {
      System.out.println(a[i]);
    }
  }
}