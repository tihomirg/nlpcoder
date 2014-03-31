package examples.java.io.file;

import java.io.File;
public class T9 {
  public static void main(String[] a) {
    File myFile = new File("C:" + File.separator);
    for(File s: myFile.listFiles()){
      System.out.println(s);
    }
  }
}
