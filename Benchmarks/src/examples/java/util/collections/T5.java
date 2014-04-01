package examples.java.util.collections;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

public class T5 {
  public static void main(String args[]) throws Exception {
    Vector<String> v = new Vector<String>();
    v.add("a");
    v.add("b");
    v.add("c");

    Collection<String> col = v;
    Enumeration<String> e = Collections.enumeration(col);
    
    for (; e.hasMoreElements();) {
      Object o = e.nextElement();
      System.out.println(o);
    }
  }
} 