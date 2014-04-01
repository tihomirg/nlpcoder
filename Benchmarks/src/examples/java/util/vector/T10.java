package examples.java.util.vector;
import java.util.Enumeration;
import java.util.Vector;

public class T10 {

  public static void main(String args[]) {

    Vector vector = new Vector();
    vector.addElement(new Integer(5));
    vector.addElement(new Float(-14.14f));
    vector.addElement(new String("Hello"));

    Enumeration e = vector.elements();
    while(e.hasMoreElements()) {
      Object obj = e.nextElement();
      System.out.println(obj);
    }
  }
}