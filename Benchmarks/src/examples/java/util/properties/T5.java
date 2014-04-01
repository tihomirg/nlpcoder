package examples.java.util.properties;
import java.util.Properties;
import java.util.Set;

public class T5 {
  public static void main(String args[]) {
    Properties capitals = new Properties();

    capitals.put("K1", "V1");
    capitals.put("K2", "V2");

    Set states = capitals.keySet();

    for (Object name : states)
      System.out.println("The value of " + name + " is "
          + capitals.getProperty((String) name) + ".");
  }
}