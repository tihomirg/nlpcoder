package examples.java.io.file;
import java.io.File;

public class T1 {

  public static void main(String args[]) {

    try {

      System.out.println("pathSeparatorChar = " + File.pathSeparatorChar);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}