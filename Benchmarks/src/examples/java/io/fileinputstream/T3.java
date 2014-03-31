package examples.java.io.fileinputstream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
public class T3 {
  public static void main(String[] a) {
    File aFile = new File("C:/myFile.text");
    FileInputStream inputFile1 = null; 
    FileDescriptor fd = null; 
    try {
      inputFile1 = new FileInputStream(aFile);
      fd = inputFile1.getFD(); 
    } catch (IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
    FileInputStream inputFile2 = new FileInputStream(fd);
  }
}