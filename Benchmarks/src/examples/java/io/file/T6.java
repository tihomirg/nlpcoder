package examples.java.io.file;
import java.io.File;
import java.net.URI;

public class T6 {
  public static void main(String[] a) throws Exception {

    File remoteFile = new File(new URI("file:///index.htm"));
  }
}