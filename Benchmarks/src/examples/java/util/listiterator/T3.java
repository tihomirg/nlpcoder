package examples.java.util.listiterator;
import java.util.ArrayList;
import java.util.ListIterator;

public class T3 {
  public static void main(String[] args) {
    ArrayList<String> aList = new ArrayList<String>();

    aList.add("1");
    aList.add("2");
    aList.add("3");
    aList.add("4");
    aList.add("5");

    ListIterator<String> listIterator = aList.listIterator();
    System.out.println("Previous: " + listIterator.previousIndex());
    System.out.println("Next: " + listIterator.nextIndex());

    // advance current position by one using next method
    listIterator.next();
    System.out.println("Previous: " + listIterator.previousIndex());
    System.out.println("Next: " + listIterator.nextIndex());
  }
}