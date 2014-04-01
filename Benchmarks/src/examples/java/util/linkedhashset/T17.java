package examples.java.util.linkedhashset;
import java.util.List;
import java.util.Vector;

public class T17 {
  public static void main(String args[]) {
    Vector<String> v1 = new Vector<String>();
    v1.add("A");
    v1.add("B");
    v1.add("C");
    List l = v1.subList(1, 2);

    l.remove(0);

    System.out.println(l);
    System.out.println(v1);
  }
} 