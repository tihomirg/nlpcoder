package examples.java.util.hashset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class T10 {

  public static void main(String[] a) {
    String elements[] = { "A", "B", "C", "D", "E" };
    Set<String> set = new HashSet<String>(Arrays.asList(elements));

    Iterator iter = set.iterator();
    while (iter.hasNext()) {
      System.out.println(iter.next());
    }

  }

} 