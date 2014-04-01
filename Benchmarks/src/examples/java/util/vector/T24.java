package examples.java.util.vector;
import java.util.ListIterator;
import java.util.Vector;

public class T24 {
  public static void main(String[] args) {
    Vector<String> v = new Vector<String>();

    v.add("1");
    v.add("2");
    v.add("3");
    v.add("4");
    v.add("5");

    ListIterator itr = v.listIterator();
    while (itr.hasNext()){
      System.out.println(itr.next());
    }
    while (itr.hasPrevious()){
      System.out.println(itr.previous());
    }
  }
}