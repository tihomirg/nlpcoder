package examples.java.util.properties;
import java.util.Properties;

public class T2 {
  public static void main(String[] args) {
    Properties properties = System.getProperties();
    properties.list(System.out);
  }
}