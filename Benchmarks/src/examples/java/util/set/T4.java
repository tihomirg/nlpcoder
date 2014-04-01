package examples.java.util.set;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class T4 {
  public static void main(String args[]) throws Exception {
    String elements[] = { "I", "P", "E", "G", "P" };
    Set set = new HashSet(Arrays.asList(elements));
    Set set2 = ((Set) ((HashSet) set).clone());
    System.out.println(set2);
    FileOutputStream fos = new FileOutputStream("set.ser");
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(set);
    oos.close();
    FileInputStream fis = new FileInputStream("set.ser");
    ObjectInputStream ois = new ObjectInputStream(fis);
    Set set3 = (Set) ois.readObject();
    ois.close();
    System.out.println(set3);
  }
}