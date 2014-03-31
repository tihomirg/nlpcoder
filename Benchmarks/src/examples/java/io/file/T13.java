package examples.java.io.file;
import java.io.File;
public class T13 {
  public static void main(String[] a) {
    File file = new File("c:\\test\\test.txt");
    file.deleteOnExit();
  }
}