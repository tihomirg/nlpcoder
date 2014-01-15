import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import util.List;


//TODO process packages.txt
//1) unfold every p1.p2.p3...pn.* and increase the results for the concrete class
//2) For each concrete class find jar
//3)

public class Main {

	public static void main(String[] args){
		try {
			//TODO: Do the same for constructor and fields.
			for(Method m : Class.forName("javax.xml.bind.annotation.XmlRootElement").getDeclaredMethods()) {
			    Class<?>[] parameterTypes = m.getParameterTypes();
			    Class<?> returnType = m.getReturnType();

			    List<String> list = new List<String>();
			    
			    list.f(Boolean.toString(Modifier.isPublic(m.getModifiers()))).f(m.getName()).f("(");
			    
			    toParameters(list, parameterTypes);   
			    
			    list.f(")").f(":").f(returnType.getName());
			    
			    System.out.println(print(list));
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
