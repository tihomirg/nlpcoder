package examples.java.io.fileinputstream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class T5 {
  public static void main(String[] args) {
    boolean areFilesIdentical = true;
    File file1 = new File("c:\\file1.txt");
    File file2 = new File("c:\\file2.txt");
    if (!file1.exists() || !file2.exists()) {
      System.out.println("One or both files do not exist");
      System.out.println(false);
    }
    System.out.println("length:" + file1.length());
    if (file1.length() != file2.length()) {
      System.out.println("lengths not equal");
      System.out.println(false);
    }
    try {
      FileInputStream fis1 = new FileInputStream(file1);
      FileInputStream fis2 = new FileInputStream(file2);
      int i1 = fis1.read();
      int i2 = fis2.read();
      while (i1 != -1) {
        if (i1 != i2) {
          areFilesIdentical = false;
          break;
        }
        i1 = fis1.read();
        i2 = fis2.read();
      }
      fis1.close();
      fis2.close();
    } catch (IOException e) {
      System.out.println("IO exception");
      areFilesIdentical = false;
    }
    System.out.println(areFilesIdentical);
  }
}
