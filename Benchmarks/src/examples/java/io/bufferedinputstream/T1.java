package examples.java.io.bufferedinputstream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class T1 {

  public static void main(String args[]) {
    try {
      FileInputStream fis = new FileInputStream(args[0]);

      BufferedInputStream bis = new BufferedInputStream(fis);

      int i;
      while ((i = bis.read()) != -1) {
        System.out.println(i);
      }
      fis.close();
    } catch (Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}