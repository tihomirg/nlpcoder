package examples.java.util.vector;
import java.util.Vector;

public class T26 {
  public static void main(String args[]) {
    Vector v = new Vector(5);
    for (int i = 0; i < 10; i++) {
      v.add(0,i);
    }
    System.out.println(v.capacity());
    System.out.println(v);
    
    v.remove(new Integer(9));
    
    v.removeElement(new Integer(2));
    
    System.out.println(v);
    System.out.println(v.capacity());
    
  }
}