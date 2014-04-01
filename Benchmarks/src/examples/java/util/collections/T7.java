package examples.java.util.collections;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

public class T7 {
  public static void main(String[] args) {
    Vector<String> v = new Vector<String>();
    v.add("A");
    v.add("B");
    v.add("D");
    v.add("E");
    v.add("F");

    System.out.println(v);
    Enumeration<String> e = v.elements();

    ArrayList<String> aList = Collections.list(e);
    System.out.println(aList);
  }
}