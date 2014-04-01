package examples.java.util.hashset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class T11 {

  public static void main(String[] a) {
    String elements[] = { "A", "B", "C", "D", "E" };
    Set<String> set = new HashSet<String>(Arrays.asList(elements));

    elements = new String[] { "B", "D", "F", "G", "1", "2", "3", "4" };
    Set<String> set2 = new HashSet<String>(Arrays.asList(elements));

    set.removeAll(set2);
    System.out.println(set);
  }
} 

