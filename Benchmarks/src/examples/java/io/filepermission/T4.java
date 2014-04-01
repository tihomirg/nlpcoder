package examples.java.io.filepermission;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class T4 {
  public static void main(String[] argv) throws Exception {
    BufferedWriter out = new BufferedWriter(new FileWriter("filename", true));
    out.write("aString");
    out.close();
  }
}