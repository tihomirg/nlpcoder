package examples.java.io.fileoutputstream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class T3 {
  private static final int BSIZE = 1024;

  public static void main(String[] args) throws Exception {
    FileChannel in = new FileInputStream("source.txt").getChannel(), out = new FileOutputStream(
        "target.txt").getChannel();
    ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
    while (in.read(buffer) != -1) {
      buffer.flip();
      out.write(buffer);
      buffer.clear();
    }
  }
}