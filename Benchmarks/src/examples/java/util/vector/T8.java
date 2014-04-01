package examples.java.util.vector;
import java.util.Vector;

public class T8 {
  public static void main(String args[]) {

    // initial size is 3, increment is 2
    Vector<Integer> v = new Vector<Integer>(3, 2);

    System.out.println("Initial size: " + v.size());
    System.out.println("Initial capacity: " + v.capacity());

    v.addElement(1);
    v.addElement(2);

    System.out.println("First element: " + v.firstElement());
    System.out.println("Last element: " + v.lastElement());

    if (v.contains(3))
      System.out.println("Vector contains 3.");
  }
}