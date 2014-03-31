package examples.java.io.randomaccessfile;
import java.io.File;
import java.io.RandomAccessFile;

public class T7 {
  public static void main(String[] argv) throws Exception {
    File f = new File("filename");
    RandomAccessFile raf = new RandomAccessFile(f, "rw");

    // Read a character
    char ch = raf.readChar();

    // Seek to end of file
    raf.seek(f.length());

    // Append to the end
    raf.writeChars("aString");
    raf.close();
  }
}