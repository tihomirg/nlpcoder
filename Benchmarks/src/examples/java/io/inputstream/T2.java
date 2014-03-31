package examples.java.io.inputstream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class T2 {
  public static void main(String[] argv) throws Exception {
    File file = new File("c:\\a.bat");
    InputStream is = new FileInputStream(file);

    long length = file.length();
    if (length > Integer.MAX_VALUE) {
      System.out.println("File is too large");
    }

    byte[] bytes = new byte[(int) length];

    int offset = 0;
    int numRead = 0;
    while (numRead >= 0) {
      numRead = is.read(bytes, offset, bytes.length - offset);
      offset += numRead;
    }

    if (offset < bytes.length) {
      throw new IOException("Could not completely read file " + file.getName());
    }
    is.close();
    System.out.println(new String(bytes));
  }
}