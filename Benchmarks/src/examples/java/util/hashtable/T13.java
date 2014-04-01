package examples.java.util.hashtable;
import java.util.Enumeration;
import java.util.Hashtable;

public class T13 {

  public static void main(String args[]) {
    Hashtable hashtable = new Hashtable();
    hashtable.put("apple", "red");
    hashtable.put("strawberry", "red");

    Enumeration e = hashtable.keys();
    while(e.hasMoreElements()) {
      Object k = e.nextElement();
      Object v = hashtable.get(k);
      System.out.println("key = " + k + "; value = " + v);
    } 

  }
}