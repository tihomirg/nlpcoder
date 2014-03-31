package examples.java.io.filedescriptor;
import java.io.FileDescriptor;
import java.io.FileOutputStream;

public class T1 {
  public static void main(String[] argv) throws Exception {
    FileOutputStream os = new FileOutputStream("outfilename");
    FileDescriptor fd = os.getFD();
    os.write(1);
    os.flush();
    fd.sync();
  }
}