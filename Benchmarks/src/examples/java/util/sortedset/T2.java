package examples.java.util.sortedset;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public class T2 {
  public static void main(String args[]) throws Exception {
    String elements[] = { "I", "P", "E", "G", "P" };
    SortedSet set = new TreeSet(Arrays.asList(elements));
    System.out.println(set.tailSet("I"));
    System.out.println(set.headSet("I"));
    System.out.println(set.headSet("I\0"));
    System.out.println(set.tailSet("I\0"));
    System.out.println(set.subSet("I", "P\0"));
    System.out.println(set.subSet("I", "I\0"));
    System.out.println(set.subSet("I", "I"));
  }
}