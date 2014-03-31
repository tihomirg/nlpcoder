package examples.java.io.file;

import java.io.File;

public class T8 {
  public static void main(String[] args) {
    File[] roots = File.listRoots();

    for (int i = 0; i < roots.length; i++) {
      System.out.println(roots[i]);
      System.out.println("Free space = " + roots[i].getFreeSpace());
      System.out.println("Usable space = " + roots[i].getUsableSpace());
      System.out.println("Total space = " + roots[i].getTotalSpace());
      System.out.println();
    }
  }
}
