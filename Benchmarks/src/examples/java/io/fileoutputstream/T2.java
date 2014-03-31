package examples.java.io.fileoutputstream;
import java.io.*;

public class T2 {

  
  public static void main(String args[]) throws Exception {
    byte buf[] = new byte[]{66,67,68,69};
    OutputStream f0 = new FileOutputStream("file1.txt");
    OutputStream f1 = new FileOutputStream("file2.txt");
    OutputStream f2 = new FileOutputStream("file3.txt");
    for (int i = 0; i < buf.length; i++) {
      f0.write(buf[i]);
    }
    f0.close();
    f1.write(buf);
    f1.close();
    f2.write(buf, buf.length / 4, buf.length / 2);
    f2.close();
  }
}