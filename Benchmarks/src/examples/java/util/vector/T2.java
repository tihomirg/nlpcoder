package examples.java.util.vector;
import java.util.Vector;

public class T2 {

  public static void main(String args[]) {

    Vector v = new Vector();

    for(int i = 0; i < 5; i++) {
      v.addElement(new Double(5));
    }

    for(int i = v.size() - 1; i >= 0; i--) {
      System.out.print(v.elementAt(i) + " ");
    }
  }
}