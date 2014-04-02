package examples.java.util.vector;
import java.util.Vector;

public class T31 {
  public static void main(String args[]) {
    Vector<Integer> v = new Vector<Integer>(5);
    for (int i = 0; i < 10; i++) {
      v.add(0,i);
    }
    System.out.println(v);
  
    Vector<Integer> v2 = new Vector<Integer>(5);
    for (int i = 0; i < 5; i++) {
      v2.add(0,i);
    }
    System.out.println(v2);
    System.out.println(v2.equals(v));
    
    v.retainAll(v2);
    System.out.println(v);
  }
} 