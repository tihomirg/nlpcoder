package examples.java.util.vector;
import java.util.Vector;

public class T18 {

  public static void main(String args[]) {
    Vector vector = new Vector();
    vector.addElement(new Integer(5));
    vector.addElement(new Float(-14.14f));

    System.out.println(vector);


    String s = new String("String to be inserted");
    vector.insertElementAt(s, 1);
    System.out.println(vector);
 

    vector.removeElementAt(2);
    System.out.println(vector);
  }
}