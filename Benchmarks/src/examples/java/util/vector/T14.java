package examples.java.util.vector;
import java.util.Vector;

public class T14 {
  public static void main(String args[]) {
    Vector<String> v = new Vector<String>();
    v.add("Hello");
    v.add("Hello");
    String s = v.get(0);
    System.out.println(s);
  }
}