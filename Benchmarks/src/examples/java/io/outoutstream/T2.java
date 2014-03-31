package examples.java.io.outoutstream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class T2 {
  public static void main(String args[]) throws IOException {
    int howMany = 20;
    // To avoid resizing the buffer, calculate the size of the
    // byte array in advance.
    ByteArrayOutputStream bout = new ByteArrayOutputStream(howMany * 4);
    DataOutputStream dout = new DataOutputStream(bout);

    for (int i = 0; i <= 20; i++) {
      dout.writeInt(i);
    }

    FileOutputStream fout = new FileOutputStream("fibonacci.dat");
    try {
      bout.writeTo(fout);
      fout.flush();
    } finally {
      fout.close();
    }
  }
}