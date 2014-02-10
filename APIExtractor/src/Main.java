import java.beans.XMLDecoder;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.IOUtils;

import util.List;


//TODO process packages.txt
//1) unfold every p1.p2.p3...pn.* and increase the results for the concrete class
//2) For each concrete class find jar
//3)

public class Main {

	public static void main(String[] args){
		try {
			URLScanner urlscan = new URLScanner();
			
			try {
				urlscan.scan("http://download.java.net/maven/2/");

			} catch (Throwable e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
			JarTrie trie = urlscan.processJars();
			
			System.out.println();
			System.out.println();
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.println();
			System.out.println();				
			
			trie.print();
			
			
			//org.apache.commons.io.FileUtils.copyURLToFile(new URL("http://download.java.net/maven/2/activecluster/activecluster/4.0.2/activecluster-4.0.2.jar"), new File("C:\\Users\\gvero\\Downloads\\activecluster-4.0.2.jar"));
			
//			String pathToJar = "http://download.java.net/maven/2/activecluster/activecluster/4.0.2/activecluster-4.0.2.jar";
//			
//			//"C:\\Users\\gvero\\Downloads\\accumulo-core-1.5.0.jar";
//			
//            JarFile jarFile = new JarFile(pathToJar);
//            Enumeration<JarEntry> e = jarFile.entries();
//
//            URL[] urls = { new URL("jar:file:" + pathToJar+"!/") };
//            ClassLoader cl = URLClassLoader.newInstance(urls);
//
//            while (e.hasMoreElements()) {
//                JarEntry je = (JarEntry) e.nextElement();
//                if(je.isDirectory() || !je.getName().endsWith(".class")){
//                    continue;
//                }
//                // -6 because of .class
//                je.getName();
//                
//                //String className = je.getName().substring(0,je.getName().length()-6);
//                //className = className.replace('/', '.');
//                //Class c = cl.loadClass(className);
//
//            }			
			
			
//			ClassLoader loader = ClassLoader.getSystemClassLoader();
//			Class c = loader.loadClass("C:\\Users\\gvero\\Download\\accumulo-core-1.5.0.jar\\org.apache.accumulo.core.bloomfilter.Filter");
//			
//			//TODO: Do the same for constructor and fields.
//			for(Method m : Class.forName("javax.xml.bind.annotation.XmlRootElement").getDeclaredMethods()) {
//			    Class<?>[] parameterTypes = m.getParameterTypes();
//			    Class<?> returnType = m.getReturnType();
//
//			    List<String> list = new List<String>();
//			    
//			    list.f(Boolean.toString(Modifier.isPublic(m.getModifiers()))).f(m.getName()).f("(");
//			    
//			    toParameters(list, parameterTypes);   
//			    
//			    list.f(")").f(":").f(returnType.getName());
//			    
//			    System.out.println(print(list));
//			}
            
//		} catch(IOException e){
//			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void toParameters(List<String> list, Class<?>[] parameters){
		for(Class<?> param: parameters){
			list.f(param.getName());
		}
	}
	
	private static String print(List<String> list){
		String s = "";
		for(String elem:list){
			s+=elem+" ";
		}
		return s;
	}
}
