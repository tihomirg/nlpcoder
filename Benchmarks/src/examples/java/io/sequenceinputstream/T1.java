package examples.java.io.sequenceinputstream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Vector;

public class T1 {

  public static void main(String[] args) throws IOException {

    Vector theStreams = new Vector();

    for (int i = 0; i < args.length; i++) {
      FileInputStream fin = new FileInputStream(args[i]);
      theStreams.addElement(fin);
    }

    InputStream in = new SequenceInputStream(theStreams.elements());
    for (int i = in.read(); i != -1; i = in.read()) {
      System.out.write(i);
    }
  }
}