package examples.java.io.filenotfoundexception;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class T1 {
  public static void main(String[] a) {
    FileInputStream inputFile = null;
    try {
      inputFile = new FileInputStream("C:/myFile.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace(System.err);
    }
  }
}