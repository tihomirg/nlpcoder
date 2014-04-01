package examples.java.util.collections;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class T3 {
  public static void main(String args[]) throws Exception {
    String elements[] = { "S", "P", "E","G", "P" };
    Set set = new TreeSet(Collections.reverseOrder());
    for (int i = 0, n = elements.length; i < n; i++) {
      set.add(elements[i]);
    }
    System.out.println(set);
    System.out.println(((TreeSet) set).comparator());
  }
}