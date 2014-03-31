package examples.java.io.randomaccessfile;
import java.io.IOException;
import java.io.RandomAccessFile;

public class T13 {
  public static void main(String[] args) throws IOException {
    RandomAccessFile raf = new RandomAccessFile("employee.dat", "rw");

    raf.writeUTF("J");
    raf.writeUTF("S");
    raf.writeDouble(4.0);
    raf.seek(0L);
    String fname = raf.readUTF();
    String lname = raf.readUTF();
    double salary = raf.readDouble();
    System.out.println("First name = " + fname);
    System.out.println("Last name = " + lname);
    System.out.println("Salary = " + salary);
    raf.close();
  }
}