package examples.java.io.file;

import java.io.File;


/*
 */
public class T12 {
  public static void main(String[] args) {
    File bkup = new File("Delete.java~");
    bkup.delete();
  }

}
