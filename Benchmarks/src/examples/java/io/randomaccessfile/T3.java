package examples.java.io.randomaccessfile;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class T3 {
  public static void main(String args[]) {
    RandomAccessFile randomAccessFile;
    FileChannel fileChannel;
    ByteBuffer byteBuffer;

    try {
      randomAccessFile = new RandomAccessFile("test.txt", "rw");
      fileChannel = randomAccessFile.getChannel();
      byteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 26);
      for (int i = 0; i < 10; i++)
        byteBuffer.put((byte) ('A' + i));
      fileChannel.close();
      randomAccessFile.close();
    } catch (IOException exc) {
      System.out.println(exc);
      System.exit(1);
    }
  }
}