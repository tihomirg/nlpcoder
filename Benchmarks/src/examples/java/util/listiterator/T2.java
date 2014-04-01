package examples.java.util.listiterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class T2 {
  public static void main(String args[]) {
    ArrayList<String> al = new ArrayList<String>();

    al.add("C");
    al.add("A");
    al.add("E");
    al.add("B");
    al.add("D");
    al.add("F");

    System.out.print("Original contents of al: ");
    Iterator<String> itr = al.iterator();
    while (itr.hasNext()) {
      String element = itr.next();
      System.out.print(element + " ");
    }
    System.out.println();

    ListIterator<String> litr = al.listIterator();
    while (litr.hasNext()) {
      String element = litr.next();
      litr.set(element + "+");
    }

    // Now, display the list backwards.
    System.out.print("Modified list backwards: ");
    while (litr.hasPrevious()) {
      String element = litr.previous();
      System.out.print(element + " ");
    }
  }
}
