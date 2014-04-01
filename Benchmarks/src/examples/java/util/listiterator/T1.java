package examples.java.util.listiterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class T1{
  public static void main(String[] a) {
    List<String> list = new LinkedList<String>();
    list.add("A");
    list.add("B");
    list.add("C");
    list.add("D");

    ListIterator<String> iter = list.listIterator(list.size());

    while (iter.hasPrevious()) {
      System.out.println(iter.previous());
      iter.add("a");
      break;
    }
    System.out.println(list);
  }
}