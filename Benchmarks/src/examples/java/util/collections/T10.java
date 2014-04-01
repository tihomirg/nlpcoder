package examples.java.util.collections;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class T10 {
  public static void main(String[] args) {
    List list = Collections.nCopies(5, "A");
    Iterator itr = list.iterator();
    while (itr.hasNext())
      System.out.println(itr.next());
  }
}