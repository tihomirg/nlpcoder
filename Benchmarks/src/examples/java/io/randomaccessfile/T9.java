package examples.java.io.randomaccessfile;
import java.io.IOException;
import java.io.RandomAccessFile;

public class T9 {
  public static void main(String[] args) {
    try {
      RandomAccessFile raf = new RandomAccessFile("c:\\temp\\RAFsample.txt", "rw");
      raf.writeInt(10);
      raf.writeInt(43);
      raf.writeInt(88);
      raf.writeInt(455);

      raf.seek((3 - 1) * 4);
      raf.writeInt(99);
      raf.seek(0); 
      int i = raf.readInt();
      while (i != -1) {
        System.out.println(i);

        i = raf.readInt();
      }
      raf.close();
    } catch (IOException e) {
    }
  }
}