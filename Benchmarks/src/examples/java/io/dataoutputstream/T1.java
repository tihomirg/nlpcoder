package examples.java.io.dataoutputstream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class T1 {

  public static void main(String args[]) {
    try {

      FileOutputStream fos = new FileOutputStream(args[0]);

      DataOutputStream dos = new DataOutputStream(fos);

      dos.writeBoolean(false);
      dos.writeByte(Byte.MAX_VALUE);
      dos.writeChar('A');
      dos.writeDouble(Double.MAX_VALUE);
      dos.writeFloat(Float.MAX_VALUE);
      dos.writeInt(Integer.MAX_VALUE);
      dos.writeLong(Long.MAX_VALUE);
      dos.writeShort(Short.MAX_VALUE);

      fos.close();
    } catch (Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}       