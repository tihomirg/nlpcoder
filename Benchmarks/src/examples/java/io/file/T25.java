package examples.java.io.file;

import java.io.File;

public class T25 {

  public static void main(String[] args) throws Exception {

    File f = new File("f");

    if (!f.setReadOnly()) {
      System.out.println("Grrr! Can't set file read-only.");
      return;
    }

  }

}
