package examples.java.io.datainputstream;
import java.io.DataInputStream;
import java.io.FileInputStream;

public class T1 {

  public static void main(String args[]) {
    try {

      FileInputStream fis = new FileInputStream("fileName.dat");

      // Create a data input stream
      DataInputStream dis = new DataInputStream(fis);

      // Read and display data
      System.out.println(dis.readBoolean());
      System.out.println(dis.readByte());
      System.out.println(dis.readChar());
      System.out.println(dis.readDouble());
      System.out.println(dis.readFloat());
      System.out.println(dis.readInt());
      System.out.println(dis.readLong());
      System.out.println(dis.readShort());

      // Close file input stream
      fis.close();
    } catch (Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}