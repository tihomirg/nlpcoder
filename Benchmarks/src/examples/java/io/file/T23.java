package examples.java.io.file;

import java.io.File;

public class T23 {

  public static void main(String args[]) {

    try {

      File oldFile = new File(args[0]);

      File newFile = new File(args[1]);

      boolean result = oldFile.renameTo(newFile);

      System.out.println(result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
