package examples.java.util.serviceloader;
import java.util.ServiceLoader;

import javax.tools.JavaCompiler;

public class T1 {
  public static void main(String[] args) {
    ServiceLoader<JavaCompiler> compilers = ServiceLoader.load(JavaCompiler.class);
    System.out.println(compilers.toString());

    for (JavaCompiler compiler : compilers)
      System.out.println(compiler);
  }
}