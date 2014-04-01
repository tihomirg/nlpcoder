package examples.java.util.vector;
import java.util.Vector;

public class T33 {
  public static void main(String args[]) {
    Vector<Object> v = new Vector<Object>(5);
    for (int i = 0; i < 10; i++) {
      v.add(0,i);
    }
    System.out.println(v);
    v.setSize(3);
    System.out.println(v);
  }
}