package examples.java.util.set;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class T12 {
  public static void main(String[] args) {

    // Create two sets.
    Set s1 = new HashSet();
    s1.add("A");
    s1.add("B");
    s1.add("C");

    Set s2 = new HashSet();
    s2.add("A");
    s2.add("B");

    Set union = new TreeSet(s1);
    union.addAll(s2); // now contains the union

    print("union", union);

    Set intersect = new TreeSet(s1);
    intersect.retainAll(s2);

    print("intersection", intersect);

  }

  protected static void print(String label, Collection c) {

    System.out.println("--------------" + label + "--------------");

    Iterator it = c.iterator();
    while (it.hasNext()) {
      System.out.println(it.next());
    }
  }
}