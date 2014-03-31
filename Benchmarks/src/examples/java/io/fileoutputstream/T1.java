package examples.java.io.fileoutputstream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class T1 {
  public static void main(String[] args) {
    try {
      File tempFile = File.createTempFile("myfile", ".tmp");
      FileOutputStream fout = new FileOutputStream(tempFile);
      PrintStream out = new PrintStream(fout);
      out.println("some text");
    } catch (IOException ex) {
      System.out.println("There was a problem creating/writing to the temp file");
      ex.printStackTrace();
    }
  }
}