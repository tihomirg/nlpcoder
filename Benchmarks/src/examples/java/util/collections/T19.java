package examples.java.util.collections;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class T19 {
  public static void main(String args[]) {
    Set simpsons = new HashSet();
    simpsons.add("B");
    simpsons.add("H");
    simpsons.add("L");
    simpsons = Collections.synchronizedSet(simpsons);
    synchronized (simpsons) {
      Iterator iter = simpsons.iterator();
      while (iter.hasNext()) {
        System.out.println(iter.next());
      }
    }
    Map map = Collections.synchronizedMap(new HashMap(89));
    Set set = map.entrySet();
    synchronized (map) {
      Iterator iter = set.iterator();
      while (iter.hasNext()) {
        System.out.println(iter.next());
      }
    }
  }
}