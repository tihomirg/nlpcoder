package examples.java.util.arrays;
import java.util.Arrays;

class Person implements Comparable<Person> {
  public Person(String firstName, String surname) {
    this.firstName = firstName;
    this.surname = surname;
  }
  public String toString() {
    return firstName + " " + surname;
  }
  public int compareTo(Person person) {
    int result = surname.compareTo(person.surname);
    return result == 0 ? firstName.compareTo(((Person) person).firstName) : result;
  }
  private String firstName;
  private String surname;
}

public class T30 {
  public static void main(String[] a) {
    Person[] people = new Person[100];
    Arrays.fill(people, 0, 50, new Person("A", "B"));
    Arrays.fill(people, 50, 100, new Person("C", "D"));
    for (Person person : people) {
      System.out.println(person);
    }
  }
}