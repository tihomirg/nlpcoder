package examples.java.io.randomaccessfile;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class T4 {
  public static void main(String[] argv) throws IOException {
    RandomAccessFile randomAccessFile = new RandomAccessFile("test.dat", "r");

    randomAccessFile.seek(1000);

    FileChannel fileChannel = randomAccessFile.getChannel();

    // This will print "1000"
    System.out.println("file pos: " + fileChannel.position());

    randomAccessFile.seek(500);

    // This will print "500"
    System.out.println("file pos: " + fileChannel.position());

    fileChannel.position(200);

    // This will print "200"
    System.out.println("file pos: " + randomAccessFile.getFilePointer());
  }
}
