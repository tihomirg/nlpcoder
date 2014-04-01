package examples.java.util.collections;
import java.util.Collections;
import java.util.Vector;

public class T13 {
  public static void main(String[] args) {
    Vector<String> v = new Vector<String>();

    v.add("A");
    v.add("B");
    v.add("A");
    v.add("C");
    v.add("D");

    System.out.println(v);
    Collections.replaceAll(v, "A", "Replace All");
    System.out.println(v);
  }
}