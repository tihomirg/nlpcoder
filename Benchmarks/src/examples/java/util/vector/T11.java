package examples.java.util.vector;
import java.util.Vector;

public class T11 {
  public static void main(String args[]) {
    Vector v = new Vector(5);
    for (int i = 0; i < 10; i++) {
      v.add(0,i);
    }
    System.out.println(v);
    System.out.println(v.size());  

    v.ensureCapacity(40);
    for (int i = 0; i < 10; i++) {
      v.add(0,i);
    }
    
    System.out.println(v);
    System.out.println(v.size());
  }
}