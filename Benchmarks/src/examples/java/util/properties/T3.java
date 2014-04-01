package examples.java.util.properties;
import java.io.FileInputStream;
import java.util.Properties;

public class T3 {
  public static void main(String args[]) throws Exception {
    Properties p = new Properties();
    p.load(new FileInputStream("colon.txt"));
    p.list(System.out);
  }
}