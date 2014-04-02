package examples.java.util.hashset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class T5 {

  public static void main(String[] a) {
    String elements[] = { "A", "B", "C", "D", "E" };
    Set<String> set = new HashSet<String>(Arrays.asList(elements));

    elements = new String[] { "E", "F" };

    set.addAll(Arrays.asList(elements));

    System.out.println(set);

    set.clear();

    System.out.println(set);
  }
}