package examples.java.io.bufferedinputstream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class T2 {

  public static void main(String[] args) throws Exception {
    File file = new File("C:/ReadFile.txt");
    FileInputStream fin = new FileInputStream(file);

    BufferedInputStream bin = new BufferedInputStream(fin);
    while (bin.available() > 0) {
      System.out.println((char) bin.read());
    }
    bin.close();
  }
}
