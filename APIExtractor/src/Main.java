import java.beans.XMLDecoder;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

import util.List;


//TODO process packages.txt
//1) unfold every p1.p2.p3...pn.* and increase the results for the concrete class
//2) For each concrete class find jar
//3)

public class Main {

	public static void main(String[] args){
		try {
	
			URL url = new URL("http://download.java.net/maven/2/activecluster/activecluster/4.0.2/");
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			String body = org.apache.commons.io.IOUtils.toString(in, encoding);
			System.out.println(body);
			
			String[] splits = body.split("<A HREF=\"");
			
			//TODO: Make XML parser look nicer, this is a dirty trick.
			if(splits.length > 1){
				
				for(int i=1; i<splits.length; i++){
					String s = splits[i].substring(0, splits[i].indexOf("\""));
					System.out.println(s);
				}
			}
			
			
			
			
//			File folder = new File("http://download.java.net/maven/2/activecluster/activecluster/4.0.2/");
//	
//		    for (final File fileEntry : folder.listFiles()) {
//		        if (fileEntry.isDirectory()) {
//		            System.out.print(fileEntry.getName());
//		        } else {
//		        	System.out.print(fileEntry.getName());
//		        }
//		    }
			
			
//			String pathToJar = "C:\\Users\\gvero\\Downloads\\accumulo-core-1.5.0.jar";
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
		} //catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
 catch (IOException e) {
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
