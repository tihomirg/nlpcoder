package examples.java.util.collection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class T1 {
  public static void main(String[] a) {
    Collection<String> c = new ArrayList<String>();
    c.add("1");
    c.add("2");
    c.add("3");
    Iterator i = c.iterator();
    while (i.hasNext()) {
      System.out.println(i.next());
    }
  }
}