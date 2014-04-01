package examples.java.util.vector;
import java.util.Vector;

public class T7 {
  public static void main(String args[]) {
    Vector<String> v1 = new Vector<String>();
    v1.add("A");
    v1.add("B");
    v1.add("C");
    String array[] = new String[v1.size()];
    v1.copyInto(array);

    for (int i = 0; i < array.length; i++) {
      System.out.println(array[i]);
    }
  }
}