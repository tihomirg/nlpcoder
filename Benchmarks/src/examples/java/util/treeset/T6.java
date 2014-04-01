package examples.java.util.treeset;
import java.util.Arrays;
import java.util.TreeSet;

public class T6 {
  public static void main(String args[]) throws Exception {
    String elements[] = { "A", "C", "D", "G", "F" };
    TreeSet<String> set = new TreeSet<String>(Arrays.asList(elements));
    System.out.println(set);
    System.out.println(set.first());
    System.out.println(set.last());
  }
}