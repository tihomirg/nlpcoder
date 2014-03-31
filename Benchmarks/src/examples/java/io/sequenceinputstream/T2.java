package examples.java.io.sequenceinputstream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.SequenceInputStream;

public class T2 {
  public static void main(String args[]) throws IOException {
    FileInputStream f1 = new FileInputStream("ByteArrayIOApp.java");
    FileInputStream f2 = new FileInputStream("FileIOApp.java");
    SequenceInputStream  inStream = new SequenceInputStream(f1, f2);
    boolean eof = false;
    int byteCount = 0;
    while (!eof) {
      int c = inStream.read();
      if (c == -1)
        eof = true;
      else {
        System.out.print((char) c);
        ++byteCount;
      }
    }
    System.out.println(byteCount + " bytes were read");
    inStream.close();
    f1.close();
    f2.close();
  }
}