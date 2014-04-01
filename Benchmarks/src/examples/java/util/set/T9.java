package examples.java.util.set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class T9 {
  public static void main(String args[]) {
    String elements[] = { "I", "P", "E", "G", "P" };
    Set set = new HashSet(Arrays.asList(elements));
    Iterator iter = set.iterator();
    while (iter.hasNext()) {
      System.out.println(iter.next());
    }
  }
}