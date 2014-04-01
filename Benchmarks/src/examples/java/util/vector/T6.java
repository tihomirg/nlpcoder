package examples.java.util.vector;
import java.util.Vector;

public class T6 {
  public static void main(String args[]) {
    Vector<String> v = new Vector<String>();
    v.add("A");
    v.add("B");

    Vector<String> v2 = new Vector<String>();
    v2.add("A");
    v2.add("B");
    v2.add("C");

    boolean isContaining = v2.containsAll(v);

    System.out.println(isContaining);
  }

}