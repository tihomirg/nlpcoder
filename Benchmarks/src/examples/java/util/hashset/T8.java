package examples.java.util.hashset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class T8 {

  public static void main(String[] a) {
    String elements[] = { "A", "B", "C", "D", "E" };
    Set<String> set = new HashSet<String>(Arrays.asList(elements));

    elements = new String[] { "A", "B", "C" };
    Set<String> set2 = new HashSet<String>(Arrays.asList(elements));

    System.out.println(set.containsAll(set2));
  }
}