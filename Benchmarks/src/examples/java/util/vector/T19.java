package examples.java.util.vector;
import java.util.Vector;

public class T19 {
  public static void main(String args[]) {
    Vector v = new Vector(5);
    for (int i = 0; i < 10; i++) {
      v.add(0,i);
    }
    System.out.println(v);
  
    v.setSize(0);

    System.out.println(v);
    System.out.println(v.isEmpty());
  }
}