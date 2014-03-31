package examples.java.io.file;
import java.io.File;
public class T15 {
  public static void main(String[] a) {
    File myFile = new File("C:" + File.separator + "jdk1.5.0" + File.separator, "File.java");
    System.out.println(myFile.getAbsoluteFile());
  }
}