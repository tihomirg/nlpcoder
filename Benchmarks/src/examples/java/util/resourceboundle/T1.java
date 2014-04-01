package examples.java.util.resourceboundle;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class T1 {
  public static void main(String args[]) throws Exception {
    ResourceBundle resources;

    try {
      resources = ResourceBundle.getBundle("MyData");
      System.out.println(resources.getString("Hi"));
    } catch (MissingResourceException mre) {
      System.err.println("MyData.properties not found");
    }
  }
}