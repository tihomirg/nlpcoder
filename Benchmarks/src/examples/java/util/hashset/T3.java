package examples.java.util.hashset;
import java.util.HashSet;

public class T3 {
  public static void main(String args[]) {
    HashSet<String> hs = new HashSet<String>();

    hs.add("B");
    hs.add("A");
    hs.add("D");
    hs.add("E");
    hs.add("C");
    hs.add("F");

    System.out.println(hs);
  }
}