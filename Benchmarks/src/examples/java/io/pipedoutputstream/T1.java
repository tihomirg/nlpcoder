package examples.java.io.pipedoutputstream;
 
/*
 * Copyright (c) 2004 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 3nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose,
 * including teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book, 
 * please visit http://www.davidflanagan.com/javaexamples3.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Serializable;

/**
 * A simple class that implements a growable array of ints, and knows how to
 * serialize itself as efficiently as a non-growable array.
 */
class SerialIntList implements Serializable {
  // These are the fields of this class. By default the serialization
  // mechanism would just write them out. But we've declared size to be
  // transient, which means it will not be serialized. And we've
  // provided writeObject() and readObject() methods below to customize
  // the serialization process.
  protected int[] data = new int[8]; // An array to store the numbers.

  protected transient int size = 0; // Index of next unused element of array

  /** Return an element of the array */
  public int get(int index) {
    if (index >= size)
      throw new ArrayIndexOutOfBoundsException(index);
    else
      return data[index];
  }

  /** Add an int to the array, growing the array if necessary */
  public void add(int x) {
    if (data.length == size)
      resize(data.length * 2); // Grow array if needed.
    data[size++] = x; // Store the int in it.
  }

  /** An internal method to change the allocated size of the array */
  protected void resize(int newsize) {
    int[] newdata = new int[newsize]; // Create a new array
    System.arraycopy(data, 0, newdata, 0, size); // Copy array elements.
    data = newdata; // Replace old array
  }

  /**
   * Get rid of unused array elements before serializing the array. This may
   * reduce the number of array elements to serialize. It also makes data.length ==
   * size, so there is no need to safe the (transient) size field. The
   * serialization mechanism will automatically call this method when
   * serializing an object of this class. Note that this must be declared
   * private.
   */
  private void writeObject(ObjectOutputStream out) throws IOException {
    if (data.length > size)
      resize(size); // Compact the array.
    out.defaultWriteObject(); // Then write it out normally.
  }

  /**
   * Restore the transient size field after deserializing the array. The
   * serialization mechanism automatically calls this method.
   */
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject(); // Read the array normally.
    size = data.length; // Restore the transient field.
  }

  /**
   * Does this object contain the same values as the object o? We override this
   * Object method so we can test the class.
   */
  public boolean equals(Object o) {
    if (!(o instanceof SerialIntList))
      return false;
    SerialIntList that = (SerialIntList) o;
    if (this.size != that.size)
      return false;
    for (int i = 0; i < this.size; i++)
      if (this.data[i] != that.data[i])
        return false;
    return true;
  }

  /** We must override this method when we override equals(). */
  public int hashCode() {
    int code = 1; // non-zero to hash [0] and [] to distinct values
    for (int i = 0; i < size; i++)
      code = code * 997 + data[i]; // ignore overflow
    return code;
  }

  /** A main() method to prove that it works */
  public static void main(String[] args) throws Exception {
    SerialIntList list = new SerialIntList();
    for (int i = 0; i < 100; i++)
      list.add((int) (Math.random() * 40000));
    SerialIntList copy = (SerialIntList) Serializer.deepclone(list);
    if (list.equals(copy))
      System.out.println("equal copies");
    Serializer.store(list, new File("intlist.ser"));
  }
}

class Serializer {
  /**
   * Serialize the object o (and any Serializable objects it refers to) and
   * store its serialized state in File f.
   */
  static void store(Serializable o, File f) throws IOException {
    ObjectOutputStream out = // The class for serialization
    new ObjectOutputStream(new FileOutputStream(f));
    out.writeObject(o); // This method serializes an object graph
    out.close();
  }

  /**
   * Deserialize the contents of File f and return the resulting object
   */
  static Object load(File f) throws IOException, ClassNotFoundException {
    ObjectInputStream in = // The class for de-serialization
    new ObjectInputStream(new FileInputStream(f));
    return in.readObject(); // This method deserializes an object graph
  }

  /**
   * Use object serialization to make a "deep clone" of the object o. This
   * method serializes o and all objects it refers to, and then deserializes
   * that graph of objects, which means that everything is copied. This differs
   * from the clone() method of an object which is usually implemented to
   * produce a "shallow" clone that copies references to other objects, instead
   * of copying all referenced objects.
   */
  static Object deepclone(final Serializable o) throws IOException, ClassNotFoundException {
    // Create a connected pair of "piped" streams.
    // We'll write bytes to one, and them from the other one.
    final PipedOutputStream pipeout = new PipedOutputStream();
    PipedInputStream pipein = new PipedInputStream(pipeout);

    // Now define an independent thread to serialize the object and write
    // its bytes to the PipedOutputStream
    Thread writer = new Thread() {
      public void run() {
        ObjectOutputStream out = null;
        try {
          out = new ObjectOutputStream(pipeout);
          out.writeObject(o);
        } catch (IOException e) {
        } finally {
          try {
            out.close();
          } catch (Exception e) {
          }
        }
      }
    };
    writer.start(); // Make the thread start serializing and writing

    // Meanwhile, in this thread, read and deserialize from the piped
    // input stream. The resulting object is a deep clone of the original.
    ObjectInputStream in = new ObjectInputStream(pipein);
    return in.readObject();
  }
}

/**
 * This is a simple serializable data structure that we use below for testing
 * the methods above
 */
class DataStructure implements Serializable {
  String message;

  int[] data;

  DataStructure other;

  public String toString() {
    String s = message;
    for (int i = 0; i < data.length; i++)
      s += " " + data[i];
    if (other != null)
      s += "\n\t" + other.toString();
    return s;
  }
}

/** This class defines a main() method for testing */
public class T1 {
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    // Create a simple object graph
    DataStructure ds = new DataStructure();
    ds.message = "hello world";
    ds.data = new int[] { 1, 2, 3, 4 };
    ds.other = new DataStructure();
    ds.other.message = "nested structure";
    ds.other.data = new int[] { 9, 8, 7 };

    // Display the original object graph
    System.out.println("Original data structure: " + ds);

    // Output it to a file
    File f = new File("datastructure.ser");
    System.out.println("Storing to a file...");
    Serializer.store(ds, f);

    // Read it back from the file, and display it again
    ds = (DataStructure) Serializer.load(f);
    System.out.println("Read from the file: " + ds);

    // Create a deep clone and display that. After making the copy
    // modify the original to prove that the clone is "deep".
    DataStructure ds2 = (DataStructure) Serializer.deepclone(ds);
    ds.other.message = null;
    ds.other.data = null; // Change original
    System.out.println("Deep clone: " + ds2);
  }
}