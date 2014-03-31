package examples.java.io.datainputstream;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class T2 {
  public static void main(String[] args) throws IOException {
    DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(
        "temp.tmp")));
    for (int i = 0; i < 10; i++)
      dis.readInt();
    dis.close();

  }
}
