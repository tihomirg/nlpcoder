package examples.java.util.collections;
import java.util.Collections;
import java.util.Vector;

public class T26 {
  public static void main(String[] args) {
    Vector<String> v = new Vector<String>();

    v.add("1");
    v.add("2");
    v.add("3");
    v.add("4");
    v.add("java2s.com");

    System.out.println(v);
    Collections.swap(v, 0, 4);
    System.out.println(v);
  }
}