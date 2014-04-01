package examples.java.util.vector;
import java.util.Iterator;
import java.util.Vector;

public class T20 {
  public static void main(String args[]) {
    Vector<String> v = new Vector<String>();
    v.add("A");
    v.add("B");

    Iterator i = v.iterator();
    while (i.hasNext()) {
      System.out.println(i.next());
    }
  }
}