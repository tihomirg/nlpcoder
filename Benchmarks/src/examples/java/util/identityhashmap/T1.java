package examples.java.util.identityhashmap;
import java.util.IdentityHashMap;
import java.util.Map;

public class T1 {
  public static void main(String[] argv) throws Exception {
    Map<Object, Object> objMap = new IdentityHashMap<Object, Object>();

    Object o1 = new Integer(123);
    Object o2 = new Integer(123);
    objMap.put(o1, "first");
    objMap.put(o2, "second");

    Object v1 = objMap.get(o1); // first
    Object v2 = objMap.get(o2); // second
  }
}