package examples.java.io.file;

import java.io.File;

public class T16 {

  public static void main(String args[]) {

    try {

      File file = new File("c:\\winnt");
      System.out.println("getCanonicalPath() = " + file.getCanonicalPath());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
