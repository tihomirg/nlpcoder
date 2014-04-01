package examples.java.util.vector;
import java.util.Vector;

public class T4 {
  public static void main(String args[]) {
    Vector v = new Vector(5);
    for (int i = 0; i < 10; i++) {
      v.add(i,0);
    }
    System.out.println(v);
    
    v.clear();
    
    System.out.println(v);
  }
} 