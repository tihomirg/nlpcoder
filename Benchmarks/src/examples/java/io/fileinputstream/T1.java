package examples.java.io.fileinputstream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class T1 {
  public static void main(String[] a) {
    File aFile = new File("C:/myFile.txt");
    FileInputStream inputFile = null;
    try {
      inputFile = new FileInputStream(aFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
  }
}