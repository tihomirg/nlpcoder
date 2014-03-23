package selection.deserializers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FrequencyDeserializer {

	private Map<Integer, Integer> freq;

	public FrequencyDeserializer(String fileName) {
		this.freq = loadMap(fileName);
	}

	private Map<Integer, Integer> loadMap(String fileName){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		BufferedReader reader = null;
		
		try{
		
		  reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
		
		  String line = null;
		  while ((line = reader.readLine()) != null) {
		    String[] splits = line.split(" : ");
		    map.put(Integer.parseInt(splits[1]), Integer.parseInt(splits[0]));
		  }
		
		} catch(IOException e){
		} finally{
			
		  if (reader != null)
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
		
		return map;
	}

	public Map<Integer, Integer> getFreq() {
		return freq;
	}		
	
}
