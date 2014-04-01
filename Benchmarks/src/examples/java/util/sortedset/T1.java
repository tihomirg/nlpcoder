package examples.java.util.sortedset;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class T1 {
  public static void main(String[] argv) throws Exception {
    SortedSet<String> set = new TreeSet<String>();
    set.add("b");
    set.add("c");
    set.add("a");

    Iterator it = set.iterator();
    while (it.hasNext()) {
      Object element = it.next();
    }
    String[] array = (String[]) set.toArray(new String[set.size()]);
  }
}