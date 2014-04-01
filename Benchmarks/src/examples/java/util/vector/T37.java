package examples.java.util.vector;
import java.util.Vector;

public class T37 {
  public static void main(String args[]) {
    Vector<String> v1 = new Vector<String>();
    v1.add("A");
    v1.add("B");
    v1.add("C");
    String array[] = new String[0];
    array = (String[]) v1.toArray(array);

    for (int i = 0; i < array.length; i++) {
      System.out.println(array[i]);
    }
  }
}