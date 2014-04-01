package examples.java.util.vector;
import java.util.Vector;

public class T22 {
  static String members[] = { "A", "I", "C", "D", "E", "F", "G", "H", "I", "J" };

  public static void main(String args[]) {
    Vector v = new Vector();
    for (int i = 0, n = members.length; i < n; i++) {
      v.add(members[i]);
    }
    System.out.println(v);
    System.out.println(v.lastIndexOf("I"));
  }
}