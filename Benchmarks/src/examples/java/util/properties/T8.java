package examples.java.util.properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class T8 {
  public static void main(String args[]) throws Exception {
    Properties p = new Properties();
    p.load(new FileInputStream("test.txt"));

    p.store(new FileOutputStream("t.txt"),"no comments");
  }
}