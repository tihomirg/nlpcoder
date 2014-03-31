package examples.java.io.randomaccessfile;
import java.io.IOException;
import java.io.RandomAccessFile;

public class T6 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile file = new RandomAccessFile("test.txt", "rw");
    file.writeBoolean(true);
    file.writeInt(123456);
    file.writeChar('j');
    file.writeDouble(1234.56);
    file.seek(1);
    System.out.println(file.readInt());
    System.out.println(file.readChar());
    System.out.println(file.readDouble());
    file.seek(0);
    System.out.println(file.readBoolean());
    file.close();
  }
}
