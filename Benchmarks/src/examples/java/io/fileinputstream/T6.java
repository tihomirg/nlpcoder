package examples.java.io.fileinputstream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class T6 {
  public static void main(String args[]) {
    FileInputStream fIn;
    FileChannel fChan;
    long fSize;
    ByteBuffer mBuf;

    try {
      fIn = new FileInputStream("test.txt");

      fChan = fIn.getChannel();

      fSize = fChan.size();

      mBuf = ByteBuffer.allocate((int) fSize);

      fChan.read(mBuf);

      mBuf.rewind();

      for (int i = 0; i < fSize; i++)
        System.out.print((char) mBuf.get());
      fChan.close();
      fIn.close();
    } catch (IOException exc) {
      System.out.println(exc);
    }
  }
}