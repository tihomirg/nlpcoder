package examples.java.util.vector;
import java.util.Vector;

public class T27 {

  public static void main(String[] args) {

    Vector<String> v = new Vector<String>();

    v.add("1");
    v.add("2");
    v.add("3");

    System.out.println(v.size());

    v.clear();

    v.removeAllElements();

    System.out.println(v.size());
  }
}