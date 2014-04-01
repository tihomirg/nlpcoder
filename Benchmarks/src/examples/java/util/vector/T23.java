package examples.java.util.vector;
import java.util.Vector;

public class T23 {
  public static void main(String[] args) {
    Vector<String> v = new Vector<String>();
    v.add("1");
    v.add("2");
    v.add("3");
    v.add("4");
    v.add("5");
    v.add("1");
    v.add("2");

    System.out.println(v.indexOf("1", 4));
    System.out.println(v.lastIndexOf("2", 5));
  }
}