package examples.java.io.bufferedoutputstream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class T3 {
  public static void main(String[] args) throws Exception {
    String fromFileName = "from.txt";
    String toFileName = "to.txt";
    BufferedInputStream in = new BufferedInputStream(new FileInputStream(fromFileName));
    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(toFileName));
    byte[] buff = new byte[32 * 1024];
    int len;
    while ((len = in.read(buff)) > 0)
      out.write(buff, 0, len);
    in.close();
    out.close();
  }
}
