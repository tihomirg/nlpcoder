package examples.java.util.treeset;
import java.util.Arrays;
import java.util.TreeSet;

public class T2 {
  public static void main(String args[]) throws Exception {

    String elements[] = { "A", "C", "D", "G", "F" };
    TreeSet<String> set = new TreeSet<String>(Arrays.asList(elements));

    System.out.println(set.headSet("D"));
    System.out.println(set.tailSet(""));
  }
}