package examples.java.io.datainputstream;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class T3 {
  public static void main(String[] args) throws IOException {
    DataInputStream in = new DataInputStream(new FileInputStream("Main.class"));

    int start = in.readInt();
    if (start != 0xcafebabe) {
      System.out.println("not valid");
    }
    in.close();
    System.out.println(in.readUnsignedShort()+"/"+in.readUnsignedShort());
    
  }
}
