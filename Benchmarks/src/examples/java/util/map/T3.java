package examples.java.util.map;
import java.util.HashMap;
import java.util.Map;

class Counter {
  int i = 1;

  public String toString() {
    return Integer.toString(i);
  }
}

public class T3 {
  public static void main(String[] args) {
    Map hm = new HashMap();
    for (int i = 0; i < 10; i++) {
      Integer r = new Integer(20);
      if (hm.containsKey(r))
        ((Counter) hm.get(r)).i++;
      else
        hm.put(r, new Counter());
    }
    System.out.println(hm);
  }
}