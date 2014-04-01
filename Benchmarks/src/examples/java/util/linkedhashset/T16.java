package examples.java.util.linkedhashset;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class T16 {
  public static void main(String[] a) {
    List<String> list = new LinkedList<String>();
    list.add("A");
    list.add("B");
    list.add("C");
    list.add("D");

    ListIterator iter = list.listIterator();
    while (iter.hasNext()) {
      System.out.println(iter.next());
    }

  }
}