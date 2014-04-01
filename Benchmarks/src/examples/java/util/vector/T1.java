package examples.java.util.vector;
import java.util.Arrays;
import java.util.Vector;

public class T1 {
  public static void main(String args[]) {
    Integer[] array = {1,2,3,4,5};
    Vector<Integer> v = new Vector<Integer>(Arrays.asList(array));
    
    System.out.println(v);
  }
}