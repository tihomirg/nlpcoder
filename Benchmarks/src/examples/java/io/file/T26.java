package examples.java.io.file;

import java.io.File;
import java.io.IOException;

public class T26 {

  public static void main(String[] args) throws IOException {
    // Create a new file, by default canWrite=true,
    boolean readonly = false;
    File file = new File("test.txt");
    if (file.exists()) {
      file.delete();
    }
    file.createNewFile();
    System.out.println("Before. canWrite?" + file.canWrite());

    // set to read-only, atau canWrite = false */
    file.setWritable(false);
    System.out.println("After. canWrite?" + file.canWrite());
  }
}
