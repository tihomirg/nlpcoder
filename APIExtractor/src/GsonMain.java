import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
 
public class GsonMain {

	public static void write(Object obj, String fileName) {
		Gson gson = new Gson();
 
		// convert java object to JSON format,
		// and returned as JSON formatted string
		String json = gson.toJson(obj);
 
		try {
			//write converted json data to a file named "file.json"
			FileWriter writer = new FileWriter(fileName);
			writer.write(json);
			writer.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	public static <T> T read(Class<T> t, String fileName) {

		Gson gson = new Gson();
		T obj = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			obj = gson.fromJson(br, t);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
}
