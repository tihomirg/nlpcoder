package examples.java.io.fileoutputstream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;

public class T5 {

  public static void main(String[] args) {

    for (int i = 0; i < args.length; i++) {
      if (args[i].toLowerCase().endsWith(".dfl")) {
        try {
          FileInputStream fin = new FileInputStream(args[i]);
          InflaterInputStream iis = new InflaterInputStream(fin);
          FileOutputStream fout = new FileOutputStream(args[i].substring(0, args[i].length() - 4));
          for (int c = iis.read(); c != -1; c = iis.read()) {
            fout.write(c);
          }
          fout.close();
        } catch (IOException ex) {
          System.err.println(ex);
        }
      } else {
        System.err.println(args[i] + " does not appear to be a deflated file.");
      }
    }
  }
}