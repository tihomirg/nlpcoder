import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;


public class DeclarationScanner {

	public static void main(String[] args){
		
		String fileName = "C:/users/gvero/git/nlpcoder/APIExtractor/bin/test/CityImpl.class";
		
		List<String> content = onlyPublic(scanClassfile(fileName));
		
		
		
		
		print(content);
	}
	
	private static void print(List<String> content) {
		for(String line: content){
			System.out.println(line);
		}
		
	}

	private static List<String> onlyPublic(List<String> content){
		List<String> nContent = new LinkedList<String>();
		
		for(String line: content){
			if (line.contains("public")){
				nContent.add(line);
			}
		}
		
		return nContent;
	}

	private static List<String> scanClassfile(String fileName) {

		List<String> content = new LinkedList<String>();
		BufferedReader reader = null;
		
		try
		{
		 Runtime rt = Runtime.getRuntime() ;
		 
		 String[] argc = {"C:/Program Files/java7/jdk1.7.0_01/bin/javap.exe", fileName};
		 Process p = rt.exec(argc) ;
		 InputStream in = p.getInputStream() ;
		 OutputStream out = p.getOutputStream ();
		 InputStream err = p.getErrorStream();
		 
		 reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		 
		 String line;
		 int i = 0;
		 while((line = reader.readLine()) != null){
			 content.add(line);
		 }
		 
		 p.destroy();
		 
		} catch(Exception exc){
		} finally{
			if (reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return content;
	}
	
}
