package examples.java.util.set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class T5 {

  public static void main(String[] a) {
    String elements[] = { "A", "B", "C", "D", "E" };
    Set<String> set = new HashSet<String>(Arrays.asList(elements));

    System.out.println(set.contains("A"));
  }
}