package examples.java.io.randomaccessfile;
import java.io.RandomAccessFile;

public class T1 {

  public static void main(String args[]) {

    try {

      RandomAccessFile raf = new RandomAccessFile(args[0], "r");

      long position = raf.length();

      while (position > 0) {

        position -= 1;

        raf.seek(position);
        byte b = raf.readByte();

        System.out.print((char) b);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}