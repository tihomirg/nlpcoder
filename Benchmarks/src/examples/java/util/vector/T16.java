package examples.java.util.vector;
import java.util.Vector;

public class T16 {
  static String members[] = { "A", "B", "C"};

  public static void main(String args[]) {
    Vector v = new Vector();
    for (int i = 0, n = members.length; i < n; i++) {
      v.add(members[i]);
    }
    System.out.println(v);
    System.out.println("Contains A?: " + v.contains("A"));
    System.out.println("Where's A?: " + v.indexOf("A"));
    System.out.println("Where's B from end?: " + v.lastIndexOf("B"));
    int index = 0;
    int length = v.size();
    while ((index < length) && (index >= 0)) {
      index = v.indexOf("C", index);
      if (index != -1) {
        System.out.println(v.get(index));
        index++;
      }
    }
  }
}