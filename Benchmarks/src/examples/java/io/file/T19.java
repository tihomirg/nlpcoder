package examples.java.io.file;

import java.io.File;

public class T19 {
  public static void main(String[] a) {
    File[] drives = File.listRoots();
    for (int i = 0; i < drives.length; i++)
      System.out.println(drives[i]);
  }
}
