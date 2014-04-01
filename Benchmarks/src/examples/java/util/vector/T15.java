package examples.java.util.vector;
import java.util.Vector;

public class T15 {
  public static void main(String args[]) {
    Vector<String> v1 = new Vector<String>();
    v1.add("A");
    v1.add("B");
    v1.add("C");

    System.out.println(v1.hashCode());
  }
}