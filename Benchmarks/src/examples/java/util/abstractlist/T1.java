package examples.java.util.abstractlist;
import java.util.Iterator;
import java.util.Vector;

public class T1{
  public static void main(String args[]) {
    Vector<String> v = new Vector<String>();
    v.add("A");
    v.add("B");

    Iterator i = v.listIterator();
    while (i.hasNext()) {
      System.out.println(i.next());
    }
  }
}