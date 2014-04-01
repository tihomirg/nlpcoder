package examples.java.util.map;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class T7 {
  public static void main(String[] a) {
    Properties props = System.getProperties();
    Iterator iter = props.entrySet().iterator();

    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      System.out.println(entry.getKey() + " -- " + entry.getValue());
    }

  }
}
