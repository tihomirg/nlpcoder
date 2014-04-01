package examples.java.util.vector;
import java.util.List;
import java.util.Vector;

public class T35 {
  public static void main(String args[]) {
    Vector<String> v1 = new Vector<String>();
    v1.add("A");
    v1.add("B");
    v1.add("C");
    List l = v1.subList(1, 2);

    for (int i = 0; i < l.size(); i++) {
      System.out.println(l.get(i));
    }
  }
}