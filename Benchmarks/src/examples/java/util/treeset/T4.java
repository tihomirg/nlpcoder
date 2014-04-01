package examples.java.util.treeset;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class T4 {
  public static void main(String args[]) throws Exception {
    String elements[] = { "A", "C", "D", "G", "F" };
    Set<String> set = new TreeSet<String>(Collections.reverseOrder());
    for (int i = 0, n = elements.length; i < n; i++) {
      set.add(elements[i]);
    }
    System.out.println(set);
    System.out.println(((TreeSet) set).comparator());
  }
}