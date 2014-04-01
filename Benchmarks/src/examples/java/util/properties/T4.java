package examples.java.util.properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;

public class T4 {
  public static void main(String args[]) throws Exception {
    Properties p = new Properties();

    p.put("today", new Date().toString());
    p.put("user", "A");

    FileOutputStream out = new FileOutputStream("user.props");
    p.storeToXML(out, "updated");

    FileInputStream in = new FileInputStream("user.props");

    p.loadFromXML(in);
    p.list(System.out);
  }
}